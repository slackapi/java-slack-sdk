---
layout: ja
title: "ワークフローステップ"
lang: ja
---

# ワークフローステップ

（アプリによる）ワークフローステップ（Workflow Steps from Apps) は、[ワークフロービルダー](https://api.slack.com/workflows)におけるワークフローに組み込み可能なカスタムのワークフローステップを任意の Slack アプリが提供することを可能とします。

ワークフローステップは、三つの異なるユーザーイベントから構成されます:

- ワークフロー作成者がワークフローにカスタムステップを追加・または編集する
- ワークフロー作成者がステップの設定を保存・更新する
- ワークフローの利用者がそのワークフローステップを実行する

ワークフローステップを機能させるためには、これら三つのイベント全てを適切にハンドリングする必要があります。

ワークフローステップのさらなる詳細については [API ドキュメント](https://api.slack.com/workflows/steps)を参考にしてください。

### Slack アプリの設定

ワークフローステップを有効にするには [Slack アプリ管理画面](http://api.slack.com/apps)にアクセスし、開発中のアプリを選択、左ペインの **Features** > **Workflow Steps** へ遷移します。このページで以下の設定を行います。

* 機能を有効にします (**Turn on workflow steps**)
* **Add Step** ボタンをクリックして、ステップの **Name** と **Callback ID** を設定します。

また、画面上でも案内が表示されますが、**Interactivity** も有効になっている必要があります。この設定の詳細は<a href="{{ site.url | append: site.baseurl }}/guides/ja/interactive-components">インタラクティブコンポーネント</a>のページを参照してください。

---
## ステップの定義

ワークフローステップを作るための手段として Bolt は `WorkflowStep` というクラスを提供しています。

新しい `WorkflowStep` インスタンスの生成には、そのステップの `callback_id` と設定オブジェクトを渡します。

設定オブジェクトには `edit`、`save`、`execute` という三つのプロパティがあります。これらのそれぞれは単一のコールバック関数である必要があります。すべてのコールバック関数は、ワークフローステップのイベントに関する情報を保持しする `step` オブジェクトにアクセスすることができます。

`WorkflowStep` インスタンスを生成したら、それを `app.step()` メソッドに渡します。これによって、Bolt アプリは対象のワークフローステップのイベントをリッスンしたり、設定オブジェクトが提供するコールバック関数を使ってイベントに応答したりすることができるようになります。

```java
import com.slack.api.bolt.App;
import com.slack.api.bolt.middleware.builtin.WorkflowStep;

// Initiate the Bolt app as you normally would
App app = new App();

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> { return ctx.ack(); })
  .save((req, ctx) -> { return ctx.ack(); })
  .execute((req, ctx) -> { return ctx.ack(); })
  .build();

app.step(step);
```

---
## ステップの追加・編集

ワークフローの作成者が、アプリが提供するステップをワークフローに追加（またはその設定を変更）するタイミングで、アプリは [`workflow_step_edit`](https://api.slack.com/reference/workflows/workflow_step_edit) というイベントを受信します。このイベントの受信時に `WorkflowStep` 設定オブジェクト内の `edit` コールバック関数が実行されます。

このとき、ワークフロー作成・変更のどちらの場合でも、アプリは[ワークフローステップ設定のためのモーダル](https://api.slack.com/reference/workflows/configuration-view)を応答する必要があります。このモーダルは、ワークフローステップに固有の設定である必要があり、通常のモーダルにはない制約があります。最もわかりやすいものとしては、`title​`、`submit​`、`close` プロパティを設定することができません。また、デフォルトの設定では、この設定モーダルの `callback_id` はワークフローステップのものと同じものが使用されます。

`edit` コールバック関数の中では モーダルの view のうち `blocks` だけを渡すだけで簡単にステップ設定モーダルをオープンすることができる `configure()` というユーティリティ関数が利用できます。これは、必要な入力内容が揃うまで設定の保存を無効にする `submit_disabled` というオプションを `true` に設定します。

設定モーダルを開く処理に関するさらなる詳細は、[ドキュメント](https://api.slack.com/workflows/steps#handle_config_view)を参考にしてください。

```java
import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.plainTextInput;

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> {
    ctx.configure(asBlocks(
      section(s -> s.blockId("intro-section").text(plainText("text"))),
      input(i -> i
        .blockId("task_name_input")
        .element(plainTextInput(pti -> pti.actionId("task_name")))
        .label(plainText("Task name"))
      ),
      input(i -> i
        .blockId("task_description_input")
        .element(plainTextInput(pti -> pti.actionId("task_description")))
        .label(plainText("Task description"))
      ),
      input(i -> i
        .blockId("task_author_input")
        .element(plainTextInput(pti -> pti.actionId("task_author")))
        .label(plainText("Task author"))
      )
    ));
    return ctx.ack();
  })
  .save((req, ctx) -> { return ctx.ack(); })
  .execute((req, ctx) -> { return ctx.ack(); })
  .build();

app.step(step);
```

---
## ステップの設定の保存

ワークフローステップの設定モーダルが開いたら、アプリはワークフロー作成者がモーダルを送信するリクエストである `view_submission` リクエストを待ち受けます。このリクエストを受信すると `WorkflowStep` 設定オブジェクト内の `save` コールバック関数が実行されます。

`save` コールバック関数の中では、以下の引数を渡してステップの設定を保存するための `update()` 関数を利用できます。

- `inputs` は、ワークフローステップ実行時にアプリが受け取ることを期待するデータの内容を表現するオブジェクトです
- `outputs` は、ステップの実行が正常に完了したとき、同一ワークフロー内の後続のステップに提供するデータの内容を表現するオブジェクトの配列です。
- `step_name` は、デフォルトのステップ名を上書きするために使用します
- `step_image_url` は、デフォルトのステップのイメージ画像を上書きするために使用します

これら引数をどのように構成するかの詳細は、[ドキュメント](https://api.slack.com/reference/workflows/workflow_step)を参考にしてください。

```java
import java.util.*;
import com.slack.api.model.workflow.*;
import static com.slack.api.model.workflow.WorkflowSteps.*;

static String extract(Map<String, Map<String, ViewState.Value>> stateValues, String blockId, String actionId) {
  return stateValues.get(blockId).get(actionId).getValue();
}

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> { return ctx.ack(); })
  .save((req, ctx) -> {
    Map<String, Map<String, ViewState.Value>> stateValues = req.getPayload().getView().getState().getValues();
    Map<String, WorkflowStepInput> inputs = new HashMap<>();
    inputs.put("taskName", stepInput(i -> i.value(extract(stateValues, "task_name_input", "task_name"))));
    inputs.put("taskDescription", stepInput(i -> i.value(extract(stateValues, "task_description_input", "task_description"))));
    inputs.put("taskAuthorEmail", stepInput(i -> i.value(extract(stateValues, "task_author_input", "task_author"))));
    List<WorkflowStepOutput> outputs = asStepOutputs(
      stepOutput(o -> o.name("taskName").type("text").label("Task Name")),
      stepOutput(o -> o.name("taskDescription").type("text").label("Task Description")),
      stepOutput(o -> o.name("taskAuthorEmail").type("text").label("Task Author Email"))
    );
    ctx.update(inputs, outputs);
    return ctx.ack();
  })
  .execute((req, ctx) -> { return ctx.ack(); })
  .build();

app.step(step);
```

---
## ステップの実行

ワークフローの利用者によって、アプリが提供するカスタムのワークフローステップが実行されるとき、アプリは[`workflow_step_execute`](https://api.slack.com/events/workflow_step_execute) というイベントを受信します。このイベントの受信時に `WorkflowStep` 設定オブジェクト内の `execute` コールバック関数が実行されます。

`save` コールバック関数で予め規定された `inputs` の情報を使って、ここでの処理は、サードパーティの API を呼び出したり、データベースに情報を保存したり、そのユーザーのホームタブを更新したり、`outputs` オブジェクトを構築することで後続のワークフローステップが利用できる情報を設定したりします。

`execute` コールバック関数内では、ステップの実行が成功であることを Slack 側に伝える `complete()` 関数、失敗であることを伝える `fail()` 関数のいずれかを呼び出す必要があります。

```java
import java.util.*;
import com.slack.api.model.workflow.*;

WorkflowStep step = WorkflowStep.builder()
  .callbackId("copy_review")
  .edit((req, ctx) -> { return ctx.ack(); })
  .save((req, ctx) -> { return ctx.ack(); })
  .execute((req, ctx) -> {
    WorkflowStepExecution wfStep = req.getPayload().getEvent().getWorkflowStep();
    Map<String, Object> outputs = new HashMap<>();
    outputs.put("taskName", wfStep.getInputs().get("taskName").getValue());
    outputs.put("taskDescription", wfStep.getInputs().get("taskDescription").getValue());
    outputs.put("taskAuthorEmail", wfStep.getInputs().get("taskAuthorEmail").getValue());
    try {
      ctx.complete(outputs);
    } catch (Exception e) {
      Map<String, Object> error = new HashMap<>();
      error.put("message", "Something wrong!");
      ctx.fail(error);
    }
    return ctx.ack();
  })
  .build();

app.step(step);
```
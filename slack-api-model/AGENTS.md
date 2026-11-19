# AGENTS.md — slack-api-model

Instructions for AI coding agents working on this module.

## Module Overview

The `slack-api-model` module contains all core Slack data types: Block Kit blocks, elements, composition objects, event types, views, and other shared model objects. All other modules in the SDK depend on this one.

## Architecture

### Source Layout

```txt
src/main/java/com/slack/api/model/
├── block/                          # Block Kit UI framework types
│   ├── element/                    # Interactive elements (buttons, menus, inputs, etc.)
│   ├── composition/                # Reusable objects (text, options, confirmations, etc.)
│   ├── LayoutBlock.java            # Interface for all layout blocks
│   ├── BlockElement.java           # Abstract base for interactive elements (in element/)
│   ├── ContextBlockElement.java    # Interface for elements in context blocks
│   ├── ContextActionsBlockElement.java # Interface for elements in context actions blocks
│   ├── Blocks.java                 # Static factory helpers for layout blocks
│   ├── *Block.java                 # Concrete layout block types
│   └── Unknown*.java               # Fallback types for unknown JSON type values
├── event/                          # Slack event type definitions
├── view/                           # Modal and Home tab view types
└── ...                             # Other model types (File, Message, User, etc.)
```

### Key Design Principles

- **Gson deserialization via type factories** — Block Kit uses a manual discriminator pattern. Each category has a Gson factory (`GsonLayoutBlockFactory`, `GsonBlockElementFactory`, etc.) that reads the JSON `"type"` field and maps it to the concrete Java class. Unknown types fall through to `Unknown*` classes instead of throwing.
- **Lombok annotations** — All model types use `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`. Layout blocks don't need `@EqualsAndHashCode(callSuper = false)` (they implement an interface), but elements do (they extend an abstract class).
- **Static factory helpers** — `Blocks.java` and `BlockElements.java` provide convenience methods for constructing types via the `ModelConfigurator` lambda pattern.

## Adding a New Block Kit Type

Block Kit types span three categories. The steps differ slightly for each:

| Category | Location | Base Type | Gson Factory |
|----------|----------|-----------|--------------|
| Layout blocks | `block/*.java` | `LayoutBlock` (interface) | `GsonLayoutBlockFactory` |
| Block elements | `block/element/*.java` | `BlockElement` (abstract class) | `GsonBlockElementFactory` |
| Composition objects | `block/composition/*.java` | N/A (standalone `@Data` classes) | `GsonTextObjectFactory` (text objects only) |

### Steps

#### 1. Look up the Block Kit documentation

Check the Slack API reference:
- Layout blocks: `https://docs.slack.dev/reference/block-kit/blocks/{block-type}`
- Elements: `https://docs.slack.dev/reference/block-kit/block-elements/{element-type}`
- Composition objects: `https://docs.slack.dev/reference/block-kit/composition-objects/{object-type}`

#### 2. Create the Java class

##### For a layout block

File: `src/main/java/com/slack/api/model/block/{Name}Block.java`

```java
package com.slack.api.model.block;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://docs.slack.dev/reference/block-kit/blocks/{type}-block
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardBlock implements LayoutBlock {
    public static final String TYPE = "card";
    private final String type = TYPE;

    private String blockId;

    // Add fields from the API docs (camelCase in Java, auto-mapped to snake_case JSON)
}
```

For container blocks (those whose primary purpose is holding child elements), initialize the list with `@Builder.Default`:

```java
@Builder.Default
private List<ContextActionsBlockElement> elements = new ArrayList<>();
```

##### For a block element

File: `src/main/java/com/slack/api/model/block/element/{Name}Element.java`

```java
package com.slack.api.model.block.element;

import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.composition.ConfirmationDialogObject;
import lombok.*;

/**
 * https://docs.slack.dev/reference/block-kit/block-elements/{type}-element
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class DatePickerElement extends BlockElement {
    public static final String TYPE = "datepicker";
    private final String type = TYPE;

    private String actionId;
    private PlainTextObject placeholder;
    private String initialDate;
    private ConfirmationDialogObject confirm;
    private Boolean focusOnLoad;
}
```

##### Key conventions

- The `TYPE` constant must match exactly what the Slack API returns in the `"type"` JSON field.
- Java fields are camelCase. Gson's `LOWER_CASE_WITH_UNDERSCORES` policy handles snake_case mapping automatically.
- **List initialization depends on the field's role:**
  - Container blocks whose primary purpose is holding child elements (e.g., `ActionsBlock`, `ContextBlock`, `ContextActionsBlock`) initialize their `elements` list with `@Builder.Default` and `new ArrayList<>()`.
  - Optional/secondary list fields (e.g., `SectionBlock.fields`) are left as `null` — null vs empty has semantic meaning for optional fields.
- Use `@SerializedName` only when the API field name doesn't follow standard snake_case.
- Nested objects use inner `@Data` classes within the block/element class.

#### 3. Register in the Gson deserialization factory

**This is the most critical step.** Without it, the type will deserialize as `UnknownBlock` or `UnknownBlockElement`.

##### For layout blocks

File: `src/main/java/com/slack/api/util/json/GsonLayoutBlockFactory.java`

Add a `case` to the `getLayoutClassInstance` switch statement:

```java
case CardBlock.TYPE:
    return CardBlock.class;
```

No import needed — the factory uses `import com.slack.api.model.block.*` which covers all block classes.

##### For block elements

File: `src/main/java/com/slack/api/util/json/GsonBlockElementFactory.java`

Add a `case` to the switch statement in `getContextBlockElementClassInstance` (note: the method name is a historical misnomer — it handles all block elements, not just context ones):

```java
case SomeNewElement.TYPE:
    return SomeNewElement.class;
```

No import needed — the factory uses `import com.slack.api.model.block.element.*` which covers all element classes.

##### Other factories

Depending on where the element can appear, you may also need to register it in:
- `GsonContextBlockElementFactory.java` — for elements allowed inside `ContextBlock`
- `GsonContextActionsBlockElementFactory.java` — for elements allowed inside `ContextActionsBlock`
- `GsonRichTextElementFactory.java` — for rich text sub-elements
- `GsonTextObjectFactory.java` — for new text/composition object types

All factories are in `src/main/java/com/slack/api/util/json/` and are registered via `registerTypeAdapter()` in `slack-api-client/src/main/java/com/slack/api/util/json/GsonFactory.java`.

**Important:** If you are adding a `case` to an existing factory, no further registration is needed. If you create an entirely new Gson factory (rare — only when introducing a new interface like `ContextActionsBlockElement`), you must register it in both:
- `slack-api-client/src/main/java/com/slack/api/util/json/GsonFactory.java` (production)
- `slack-api-model/src/test/java/test_locally/unit/GsonFactory.java` (tests)

#### 4. Add static helper methods

##### For layout blocks — `Blocks.java`

File: `src/main/java/com/slack/api/model/block/Blocks.java`

```java
// CardBlock

public static CardBlock card(ModelConfigurator<CardBlock.CardBlockBuilder> configurator) {
    return configurator.configure(CardBlock.builder()).build();
}
```

##### For block elements — `BlockElements.java`

File: `src/main/java/com/slack/api/model/block/element/BlockElements.java`

```java
// SomeNewElement

public static SomeNewElement someNew(ModelConfigurator<SomeNewElement.SomeNewElementBuilder> configurator) {
    return configurator.configure(SomeNewElement.builder()).build();
}
```

##### For composition objects — `BlockCompositions.java`

File: `src/main/java/com/slack/api/model/block/composition/BlockCompositions.java`

```java
// SomeNewObject

public static SomeNewObject someNew(ModelConfigurator<SomeNewObject.SomeNewObjectBuilder> configurator) {
    return configurator.configure(SomeNewObject.builder()).build();
}
```

#### 5. Add Kotlin DSL support (layout blocks only, optional)

The `slack-api-model-kotlin-extension` module provides a Kotlin DSL for building blocks. This step is optional — some blocks (e.g., `MarkdownBlock`) skip it — but recommended for blocks with configurable fields so Kotlin users can build them via `withBlocks { }`. For new layout blocks, update three files:

##### 5a. Create a builder class

File: `slack-api-model-kotlin-extension/src/main/kotlin/com/slack/api/model/kotlin_extension/block/{Name}BlockBuilder.kt`

```kotlin
package com.slack.api.model.kotlin_extension.block

import com.slack.api.model.block.CardBlock

@BlockLayoutBuilder
class CardBlockBuilder : Builder<CardBlock> {
    private var _blockId: String? = null
    // private fields for each property

    fun blockId(blockId: String) {
        _blockId = blockId
    }

    // setter methods for each property

    override fun build(): CardBlock {
        return CardBlock.builder()
            .blockId(_blockId)
            // .otherField(_otherField)
            .build()
    }
}
```

##### 5b. Add method to `LayoutBlockDsl` interface

File: `slack-api-model-kotlin-extension/src/main/kotlin/com/slack/api/model/kotlin_extension/block/dsl/LayoutBlockDsl.kt`

```kotlin
/**
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/card-block">Card documentation</a>
 */
fun card(builder: CardBlockBuilder.() -> Unit)
```

##### 5c. Implement in `MultiLayoutBlockContainer`

File: `slack-api-model-kotlin-extension/src/main/kotlin/com/slack/api/model/kotlin_extension/block/container/MultiLayoutBlockContainer.kt`

```kotlin
override fun card(builder: CardBlockBuilder.() -> Unit) {
    underlying += CardBlockBuilder().apply(builder).build()
}
```

#### 6. Add tests

##### Deserialization test

File: `src/test/java/test_locally/api/model/block/BlockKitTest.java`

Test that JSON with the new type deserializes correctly:

```java
@Test
public void parseCardBlock() {
    String blocks = "[{\n" +
            "  \"type\": \"card\",\n" +
            "  \"block_id\": \"card123\"\n" +
            "}]";
    String json = "{blocks: " + blocks + "}";
    Message message = GsonFactory.createSnakeCase().fromJson(json, Message.class);
    assertThat(message, is(notNullValue()));
    assertThat(message.getBlocks().size(), is(1));
    CardBlock cardBlock = (CardBlock) message.getBlocks().get(0);
    assertThat(cardBlock.getBlockId(), is("card123"));
}
```

##### Static helper test

File: `src/test/java/test_locally/api/model/block/BlocksTest.java`

Test the `Blocks.java` convenience method:

```java
@Test
public void testCard() {
    assertThat(card(c -> c.blockId("block-id")), is(notNullValue()));
}
```

#### 7. Verify

```bash
# Install all modules locally (required for cross-module dependencies)
./scripts/install_local.sh

# Run the block kit tests
./mvnw test -pl slack-api-model '-Dtest=test_locally.api.model.block.BlockKitTest,test_locally.api.model.block.BlocksTest'

# If you added Kotlin DSL support, also test:
./mvnw test -pl slack-api-model-kotlin-extension '-Dtest=test_locally.**.*Test'
```

### Summary checklist

When adding a new Block Kit type, make sure you have:

- [ ] Looked up the type docs at `https://docs.slack.dev/reference/block-kit/blocks/{type}` or `.../block-elements/{type}`
- [ ] Created the Java class in the appropriate package (`block/` for layout blocks, `block/element/` for elements, `block/composition/` for composition objects)
- [ ] Added a `case` in the appropriate Gson factory (`GsonLayoutBlockFactory` for layout blocks, `GsonBlockElementFactory` for elements)
- [ ] Added static helper method(s) in `Blocks.java` (for layout blocks), `BlockElements.java` (for elements), or `BlockCompositions.java` (for composition objects)
- [ ] (Optional) Added Kotlin DSL support: builder class, `LayoutBlockDsl` method, and `MultiLayoutBlockContainer` implementation (layout blocks with configurable fields)
- [ ] Added deserialization test in `BlockKitTest.java`
- [ ] Added static helper test in `BlocksTest.java`
- [ ] Verified tests pass

## Common Pitfalls

- **Forgetting the Gson factory registration** — the type will silently deserialize as `UnknownBlock`/`UnknownBlockElement` with no error. Always add the `case` statement.
- **Wrong factory** — layout blocks go in `GsonLayoutBlockFactory`, elements in `GsonBlockElementFactory`. Registering in the wrong one means it won't deserialize in the right context.
- **Missing Kotlin DSL** — if you add a layout block with configurable fields but skip the Kotlin extension, users of `withBlocks { }` DSL won't have access to the new type. This is optional but recommended.
- **Java 9+ APIs** — the project targets Java 8. Do not use `List.of()`, `Map.of()`, `var`, etc.

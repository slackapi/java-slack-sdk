# slack.dev/java-slack-sdk

This website is built using [Docusaurus](https://docusaurus.io/). 'Tis cool.

Each Bolt/SDK has its own Docusaurus website, with matching CSS and nav/footer. There is also be a Docusaurus website of just the homepage and community tools. 

```
java-slack-sdk/
├── .github/
│   └── workflows/
│       └── deploy.yaml (deploys to site, and inserts values from version-config.yml)
├── docs/ (the good stuff. md/mdx files supported)
│   ├── index.md
│   └── guides
│       └── bolt-basics.md
├── i18n/ja-jp/ (the japanese translations)
│   ├── docusaurus-theme-classic/ (footer/navbar translations)
│   └── docusaurus-plugin-content-docs/
│       └── current/ ( file names need to exactly match **/docs/, but japanese content)
│           ├── index.md
│           └── guides
│               └── bolt-basics.md
├── static/
│   ├── css/
│   │   └── custom.css (the css for everything!)
│   ├── img/ (the pictures for the site)
│   │   ├── rory.png 
│   │   └── oslo.svg  
├── src/
│   ├── pages/ (stuff that isn't docs. This is empty for this repo!)
│   └── theme/ (only contains the 404 page)
├── version-config.yml (Where version numbers are set)
├── docusaurus.config.js (main config file. also where to set navbar/footer)
└── sidebar.js (manually set where the docs are in the sidebar.)
```

A cheat-sheet:
* _I want to edit a doc._ `docs/*/*.md`
* _I want to edit a Japanese doc._ `i18n/ja-jp/docusaurus-plugin-content-docs/current/*/*.md`. See the [Japanese docs README](https://github.com/slackapi/java-slack-sdk/blob/master/docs/i18n/ja-jp/README.md)
* _I want to update the version values of modules._ `version-config.yml`
* _I want to change the docs sidebar._ `sidebar.js`
* _I want to change the css._ Don't use this repo, use the home repo and the changes will propagate here.
* _I want to change anything else._ `docusaurus.config.js`

----

## Adding a doc

1. Make a markdown file. Add a `# Title` or use [front matter](https://docusaurus.io/docs/next/create-doc) with `title:`. 
2. Save it in `docs/folder/title.md` or `docs/title.md`, depending on if it's in a sidebar category. The nuance is just for internal organization.
3. Add the doc's path to the sidebar within `docusaurus.config.js`. Where ever makes most sense for you.
4. Test the changes ↓

---

## Running locally

Docusaurus requires at least Node 18. You can update Node however you want. `nvm` is one way. 

Install `nvm` if you don't have it:

```
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.7/install.sh | bash
```

Then grab the latest version of Node.

```
nvm install node
```


If you are running this project locally for the first time, you'll need to install the packages with the following command:

```
npm install
```

The following command starts a local development server and opens up a browser window. 

```
npm run start
```

Edits to pages are reflected live — no restarting the server or reloading the page. (I'd say... 95% of the time, and 100% time if you're just editing a markdown file).

Placeholders for version values (such as `sdkLatestVersion`) are only updated on build, not when running locally. See below for more info. 

Remember — you're only viewing the Java Slack SDK docs right now.

#### Running locally in Japanese

For local runs, Docusaurus treats each language as a different instance of the website. You'll want to specify the language to run the japanese site locally:

```
npm run start -- --locale ja-jp
```

Don't worry - both languages will be built/served on deployment. 

---

## Deploying

The following command generates static content into the `build` directory. 

```
$ npm run build
```

Then you can test out with the following command: 

```
npm run serve
```

If it looks good, make a PR request!

### Deployment to GitHub pages

There is a GitHub action workflow set up in each repo. 

* On PR, it tests a site build.
* On Merge, it builds the site and deploys it. Site should update in a minute or two.

You'll see placeholders in markdown files for version values. For example: `sdkLatestVersion`. These values are replaced by the workflow with the values set in `docs/version-config.yml`. The `sdkLatestVersion` value is updated via `scripts/set_version.sh` but the other values are changed directly in the file. 

---

## Something's broken

Luke goofed. Open an issue please! `:bufo-appreciates-the-insight:`
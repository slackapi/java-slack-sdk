import { themes as prismThemes } from "prism-react-renderer";
const footer = require('./footerConfig');
const navbar = require('./navbarConfig');

/** @type {import('@docusaurus/types').Config} */
const config = {
  title: "Java Slack SDK",
  tagline: "Java Slack SDK",
  favicon: "img/favicon.ico",

  url: "https://tools.slack.dev",
  baseUrl: "/java-slack-sdk/",
  organizationName: "slackapi",
  projectName: "java-slack-sdk",

  onBrokenLinks: "warn",
  onBrokenAnchors: "ignore",
  onBrokenMarkdownLinks: "warn",

  i18n: {
    defaultLocale: "en",
    locales: ["en", "ja-jp"],
  },

  presets: [
    [
      "classic",
      /** @type {import('@docusaurus/preset-classic').Options} */
      ({
        docs: {
          path: "content",
          breadcrumbs: false,
          routeBasePath: "/", // Serve the docs at the site's root
          sidebarPath: "./sidebars.js",
          editUrl: "https://github.com/slackapi/java-slack-sdk/tree/main/docs",
        },
        blog: false,
        theme: {
          customCss: "./src/css/custom.css",
        },
      }),
    ],
  ],

  plugins: [
    "docusaurus-theme-github-codeblock",
    [
      "@docusaurus/plugin-client-redirects",
      {
        redirects: [
          {
            to: '/guides/ai-apps', 
            from: '/guides/assistants'
          }
        ],
        createRedirects(existingPath) {
          if (existingPath.includes('/guides')) {
            // Redirect from /guides/ja/X to /guides/X
            return [
              existingPath.replace('/guides', '/guides/ja'),
            ];
          }
          return undefined; // Return a falsy value: no redirect created
        }
      }
    ]
  ],

  themeConfig:
    /** @type {import('@docusaurus/preset-classic').ThemeConfig} */
    ({
      colorMode: {
        respectPrefersColorScheme: true,
      },
      docs: {
        sidebar: {
          autoCollapseCategories: true,
        },
      },
      navbar,
      footer,
      prism: {
        additionalLanguages: ['java','groovy'],
        // switch to alucard when available in prism?
        theme: prismThemes.github,
        darkTheme: prismThemes.dracula,
      },
      codeblock: {
        showGithubLink: true,
        githubLinkLabel: "View on GitHub",
      },
      // announcementBar: {
      //   id: `announcementBar`,
      //   content: `🎉️ <b><a target="_blank" href="https://api.slack.com/">Version 2.26.0</a> of the developer tools for the Slack automations platform is here!</b> 🎉️ `,
      // },
    }),
};

export default config;

/**
 * Creating a sidebar enables you to:
 - create an ordered group of docs
 - render a sidebar for each doc of that group
 - provide next/previous navigation

 The sidebars can be generated from the filesystem, or explicitly defined here.

 Create as many sidebars as you want.
 */

// @ts-check

/** @type {import('@docusaurus/plugin-content-docs').SidebarsConfig} */
const sidebars = {
  sidebarJava: [
    {
      type: 'doc',
      id: 'index',
      label: 'Java Slack SDK',
      className: 'sidebar-title',
    },
    {type: 'html', value: '<hr>'},
    {
      type: 'category',
      label: 'Bolt for Java Guides',
      items: [
        'guides/getting-started-with-bolt',
        'guides/bolt-basics',
        'guides/supported-web-frameworks',
        'guides/socket-mode',
        'guides/events-api',
        {
					type: "category",
					label: "App UI & Interactivity",
					items: ['guides/interactive-components', 'guides/modals', 'guides/app-home',],
				},  
        'guides/ai-apps',
        'guides/shortcuts',
        'guides/slash-commands',
        {
					type: "category",
					label: "Authorization & Security",
					items: ['guides/app-distribution', 'guides/sign-in-with-slack',],
				},  
        {
					type: "category",
					label: "Admin Tools",
					items: ['guides/web-api-for-admins', 'guides/scim-api', 'guides/audit-logs-api', 'guides/status-api'],
				},    
        {
					type: "category",
					label: "Legacy",
					items: ['guides/steps-from-apps',],
				},   
      ],
    },
    {type: 'html', value: '<hr>'},
    {
      type: 'category',
      label: 'Java Slack SDK Guides',
      items: [
        'guides/web-api-basics',
        'guides/web-api-client-setup',
        'guides/composing-messages',
        'guides/incoming-webhooks',
        'guides/rtm',
        ],
    },
    {type: 'html', value: '<hr>'},
    'reference',
    {type: 'html', value: '<hr>'},
    {
      type: 'link',
      label: 'Release notes',
      href: 'https://github.com/SlackAPI/java-slack-sdk/releases',
    },
    {
      type: 'link',
      label: 'Code on GitHub',
      href: 'https://github.com/SlackAPI/java-slack-sdk',
    },
    {
      type: 'link',
      label: 'Contributors Guide',
      href: 'https://github.com/slackapi/java-slack-sdk/blob/main/.github/contributing.md',
    },
  ],
};

export default sidebars;

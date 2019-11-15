package com.github.seratch.jslack.api.model.block.element;

/**
 * <pre>
 * {
 *   "type": "rich_text",
 *   "block_id": "",
 *   "elements": [
 *     {
 *       "type": "rich_text_section",
 *       "elements": []
 *     },
 *     {
 *       "type": "rich_text_list",
 *       "elements": [],
 *       "style": "bullet",
 *       "indent": 0
 *     },
 *     {
 *       "type": "rich_text_quote",
 *       "elements": []
 *     },
 *     {
 *       "type": "rich_text_preformatted",
 *       "elements": []
 *     }
 *   ]
 * }
 * </pre>
 *
 * https://api.slack.com/changelog/2019-09-what-they-see-is-what-you-get-and-more-and-less
 */
public interface RichTextElement {

    String getType();

}

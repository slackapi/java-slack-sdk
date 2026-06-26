package com.slack.api.model.block;

/**
 * A single cell within a {@link TableBlock} row. A cell can be one of:
 *
 * <ul>
 *   <li>{@link RawTextTableCell} ({@code raw_text})</li>
 *   <li>{@link RawNumberTableCell} ({@code raw_number})</li>
 *   <li>{@link com.slack.api.model.block.RichTextBlock RichTextBlock} ({@code rich_text})</li>
 * </ul>
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/table-block">Table block</a>
 */
public interface TableCell {

    String getType();

}

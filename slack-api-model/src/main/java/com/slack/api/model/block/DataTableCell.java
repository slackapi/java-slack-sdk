package com.slack.api.model.block;

/**
 * A single cell within a {@link DataTableBlock} row. A cell can be one of:
 *
 * <ul>
 *   <li>{@link RawTextDataTableCell} ({@code raw_text})</li>
 *   <li>{@link RawNumberDataTableCell} ({@code raw_number})</li>
 *   <li>{@link com.slack.api.model.block.RichTextBlock RichTextBlock} ({@code rich_text})</li>
 * </ul>
 *
 * @see <a href="https://docs.slack.dev/reference/block-kit/blocks/data-table-block">Data table block</a>
 */
public interface DataTableCell {

    String getType();

}

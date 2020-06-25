package test_locally.view

import com.slack.api.model.block.Blocks.*
import com.slack.api.model.block.composition.BlockCompositions.*
import com.slack.api.model.kotlin_extension.view.blocks
import com.slack.api.model.view.Views.view
import org.junit.Test
import kotlin.test.assertEquals

class ViewBuilderExtensionTest {
    @Test
    fun `View builder extension works`() {
        val view = view {
            it.type("home")
                .blocks {
                    section {
                        markdownText("**Welcome to your home screen!**")
                    }
                    divider()
                    section {
                        markdownText(":clock3: Your next event starts in **10** minutes.")
                        fields {
                            plainText(":pushpin: Location: Large Conference Room", emoji = true)
                            plainText(":hourglass_flowing_sand: Duration: 30 minutes", emoji = true)
                        }
                    }
                }
        }

        val expected = view {
            it.type("home")
                    .blocks(asBlocks(
                            section { thisSection ->
                                thisSection.text(markdownText { mt -> mt.text("**Welcome to your home screen!**")})
                            },
                            divider(),
                            section { thisSection ->
                                thisSection.text(markdownText { mt -> mt.text(":clock3: Your next event starts in **10** minutes.") })
                                        .fields(asSectionFields(
                                                plainText { pt -> pt.text(":pushpin: Location: Large Conference Room").emoji(true) },
                                                plainText { pt -> pt.text(":hourglass_flowing_sand: Duration: 30 minutes").emoji(true) }
                                        ))
                            }
                    ))
        }

        assertEquals(expected.toString(), view.toString(), "$expected \n$view")
    }
}
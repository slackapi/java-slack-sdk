package test_locally.block.validation

import java.lang.reflect.Modifier
import kotlin.test.assertTrue

object ValidationUtil {

    fun validateMethodNames(target: Class<*>, builder: Class<*>) {

        val expectedNames = target
                .declaredMethods
                .filter { it.name.startsWith("set") and Modifier.isPublic(it.modifiers) }
                .map {
                    val name = it.name.replaceFirst("set", "")
                    name.first().lowercaseChar() + name.substring(1)
                }

        val builderMethodNames = builder
                .declaredMethods
                .filter { Modifier.isPublic(it.modifiers) }.map { it.name }

        for (expectedName in expectedNames) {
            assertTrue(builderMethodNames.contains(expectedName), "$expectedName is not found")
        }

    }
}
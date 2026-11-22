import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.unit.dp
import com.example.drawer.ui.AppTheme
import com.example.drawer.ui.DrawerApp
import org.junit.Rule
import org.junit.Test
import java.io.File
import javax.imageio.ImageIO

class DrawerAppTest {
    @get:Rule
    val rule = createComposeRule()

    private fun shot(name: String) {
        val dir = File(System.getProperty("screenshots.dir")).apply { mkdirs() }
        val img = rule.onNodeWithTag("phone").captureToImage().toAwtImage()
        ImageIO.write(img, "png", File(dir, "$name.png"))
    }

    private fun goTo(page: String) {
        rule.onNodeWithContentDescription("Open menu").performClick()
        rule.waitForIdle()
        rule.onNodeWithText(page).performClick()
        rule.waitForIdle()
    }

    @Test
    fun navigatesBetweenPages() {
        rule.mainClock.autoAdvance = true
        rule.setContent {
            Box(Modifier.requiredSize(412.dp, 740.dp).testTag("phone")) {
                AppTheme { DrawerApp() }
            }
        }

        rule.onNodeWithText("Welcome back 👋").assertIsDisplayed()
        shot("1-home")

        rule.onNodeWithContentDescription("Open menu").performClick()
        rule.waitForIdle()
        rule.onNodeWithText("Version 1.0").assertIsDisplayed()
        shot("2-drawer")
        rule.onNodeWithText("Profile").performClick()
        rule.waitForIdle()
        rule.onNodeWithText("Cairo, Egypt").assertIsDisplayed()
        shot("3-profile")

        goTo("Settings")
        rule.onNodeWithText("Dark mode").assertIsDisplayed()
        rule.onNodeWithText("Dark mode").performClick()
        shot("4-settings")
    }
}

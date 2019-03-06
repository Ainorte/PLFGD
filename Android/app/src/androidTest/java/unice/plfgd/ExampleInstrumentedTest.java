package unice.plfgd;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("unice.plfgd", appContext.getPackageName());
    }

    @Test
	public void TestBLABLACAR1() {
		onView(withId(R.id.home_welcome)).check(matches(isDisplayed()));          // withId(R.id.my_view) - ViewMatcher
				//.perform(click())                  // click() - ViewAction
				//.check(matches(isDisplayed()));   //matches(isDisplayed()) - ViewAssertion
	}
}

package unice.plfgd.home;


import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import unice.plfgd.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FailedConnectAndPreferencesTest {

	@Rule
	public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

	@Test
	public void failedConnectAndPreferencesTest() {
		ViewInteraction appCompatButton = onView(
				allOf(withId(R.id.valid_button), withText("Valider"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								2)));
		appCompatButton.perform(scrollTo(), click());

		ViewInteraction appCompatButton2 = onView(
				allOf(withId(R.id.connect_button), withText("Se connecter"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								1)));
		appCompatButton2.perform(scrollTo(), click());

		ViewInteraction appCompatButton3 = onView(
				allOf(withId(R.id.settings_btn), withText("Préférences"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								3)));
		appCompatButton3.perform(scrollTo(), click());

		ViewInteraction appCompatEditText = onView(
				allOf(withId(R.id.server_field), withText("10.0.2.2:10101"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.support.design.widget.CoordinatorLayout")),
										1),
								1),
						isDisplayed()));
		appCompatEditText.perform(click());

		ViewInteraction appCompatEditText2 = onView(
				allOf(withId(R.id.server_field), withText("10.0.2.2:10101"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.support.design.widget.CoordinatorLayout")),
										1),
								1),
						isDisplayed()));
		appCompatEditText2.perform(replaceText("10.0.2.2:10101"));

		ViewInteraction appCompatEditText3 = onView(
				allOf(withId(R.id.server_field), withText("10.0.2.2:10101"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.support.design.widget.CoordinatorLayout")),
										1),
								1),
						isDisplayed()));
		appCompatEditText3.perform(closeSoftKeyboard());

		ViewInteraction appCompatEditText4 = onView(
				allOf(withId(R.id.name_field),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.support.design.widget.CoordinatorLayout")),
										1),
								3),
						isDisplayed()));
		appCompatEditText4.perform(replaceText("Flo"), closeSoftKeyboard());

		ViewInteraction appCompatEditText5 = onView(
				allOf(withId(R.id.name_field), withText("Flo"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.support.design.widget.CoordinatorLayout")),
										1),
								3),
						isDisplayed()));
		appCompatEditText5.perform(pressImeActionButton());

		pressBack();

		ViewInteraction floatingActionButton = onView(
				allOf(withId(R.id.fab),
						childAtPosition(
								childAtPosition(
										withId(android.R.id.content),
										0),
								2),
						isDisplayed()));
		floatingActionButton.perform(click());

		pressBack();
	}

	private static Matcher<View> childAtPosition(
			final Matcher<View> parentMatcher, final int position) {

		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view) {
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}

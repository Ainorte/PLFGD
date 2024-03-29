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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RectangleRondTriangleTest {

	@Rule
	public ActivityTestRule<HomeActivity> mActivityTestRule = new ActivityTestRule<>(HomeActivity.class);

	@Test
	public void rectangleRondTriangleTest() {
		ViewInteraction appCompatEditText = onView(
				allOf(withId(R.id.username_field),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								1)));
		appCompatEditText.perform(scrollTo(), replaceText("J1"), closeSoftKeyboard());

		ViewInteraction appCompatButton = onView(
				allOf(withId(R.id.valid_button), withText("Valider"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								2)));
		appCompatButton.perform(scrollTo(), click());

		ViewInteraction appCompatButton2 = onView(
				allOf(withId(R.id.ent_button), withText("Entrainement"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								2)));
		appCompatButton2.perform(scrollTo(), click());

		ViewInteraction appCompatButton3 = onView(
				allOf(withId(R.id.but_ctr), withText("Rectangle Rond Triangle"),
						childAtPosition(
								childAtPosition(
										withClassName(is("android.widget.ScrollView")),
										0),
								2)));
		appCompatButton3.perform(scrollTo(), click());

		ViewInteraction view = onView(
				allOf(withId(R.id.draw_canvas),
						childAtPosition(
								childAtPosition(
										withId(R.id.contentFrame),
										0),
								2),
						isDisplayed()));
		view.check(matches(isDisplayed()));

		ViewInteraction appCompatButton4 = onView(
				allOf(withId(R.id.draw_reset), withText("Reset"),
						childAtPosition(
								childAtPosition(
										withId(R.id.contentFrame),
										0),
								1),
						isDisplayed()));
		appCompatButton4.perform(click());

		ViewInteraction appCompatButton5 = onView(
				allOf(withId(R.id.draw_valid), withText("Valider"),
						childAtPosition(
								childAtPosition(
										withId(R.id.contentFrame),
										0),
								3),
						isDisplayed()));
		appCompatButton5.perform(click());

		ViewInteraction appCompatButton6 = onView(
				allOf(withId(R.id.ctr_rejouer), withText("Reset"),
						childAtPosition(
								childAtPosition(
										withId(R.id.contentFrame),
										0),
								5),
						isDisplayed()));
		appCompatButton6.perform(click());

		ViewInteraction appCompatButton7 = onView(
				allOf(withId(R.id.draw_valid), withText("Valider"),
						childAtPosition(
								childAtPosition(
										withId(R.id.contentFrame),
										0),
								3),
						isDisplayed()));
		appCompatButton7.perform(click());

		ViewInteraction appCompatButton8 = onView(
				allOf(withId(R.id.ctr_retour), withText("Retour"),
						childAtPosition(
								childAtPosition(
										withId(R.id.contentFrame),
										0),
								4),
						isDisplayed()));
		appCompatButton8.perform(click());
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

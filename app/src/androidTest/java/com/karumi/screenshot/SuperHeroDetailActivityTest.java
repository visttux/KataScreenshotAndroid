package com.karumi.screenshot;

import android.app.Activity;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.karumi.screenshot.di.MainComponent;
import com.karumi.screenshot.di.MainModule;
import com.karumi.screenshot.model.SuperHero;
import com.karumi.screenshot.model.SuperHeroesRepository;
import com.karumi.screenshot.ui.view.SuperHeroDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import it.cosenonjaviste.daggermock.DaggerMockRule;

import static org.mockito.Mockito.when;

public class SuperHeroDetailActivityTest extends ScreenshotTest {

    @Rule public DaggerMockRule<MainComponent> daggerRule =
            new DaggerMockRule<>(MainComponent.class, new MainModule()).set(
                    new DaggerMockRule.ComponentSetter<MainComponent>() {
                        @Override
                        public void setComponent(MainComponent component) {
                            SuperHeroesApplication app =
                                    (SuperHeroesApplication) InstrumentationRegistry.getInstrumentation()
                                            .getTargetContext()
                                            .getApplicationContext();
                            app.setComponent(component);
                        }
                    }
            );

    @Rule public ActivityTestRule<SuperHeroDetailActivity> activityRule =
            new ActivityTestRule<>(SuperHeroDetailActivity.class, true, false);

    @Mock SuperHeroesRepository repository;

    @Test
    public void showAnySuperhero() {
        SuperHero superHero = givenASuperHero(false);

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    @Test
    public void showAvengerSuperhero() {
        SuperHero superHero = givenASuperHero(true);

        Activity activity = startActivity(superHero);

        compareScreenshot(activity);
    }

    private SuperHero givenASuperHero(boolean avengers) {
        String superHeroName = "SuperHero";
        String superHeroDescription = "Description Super Hero";
        SuperHero superHero = new SuperHero(superHeroName, null, avengers, superHeroDescription);
        when(repository.getByName(superHeroName)).thenReturn(superHero);
        return superHero;
    }

    private SuperHeroDetailActivity startActivity(SuperHero superHero) {
        Intent intent = new Intent();
        intent.putExtra("super_hero_name_key", superHero.getName());
        return activityRule.launchActivity(intent);
    }

}

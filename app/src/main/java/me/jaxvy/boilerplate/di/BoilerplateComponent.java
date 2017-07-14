package me.jaxvy.boilerplate.di;

import javax.inject.Singleton;

import dagger.Component;
import me.jaxvy.boilerplate.utils.InjectionBase;

@Singleton
@Component(modules = {BoilerplateModule.class})
public interface BoilerplateComponent {

    void inject(InjectionBase injectionBase);
}

package com.leaf.clips.presenter;

import android.content.ContentProvider;
import com.leaf.clips.model.InformationManager;
import dagger.MembersInjector;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated("dagger.internal.codegen.ComponentProcessor")
public final class SearchSuggestionsProvider_MembersInjector implements MembersInjector<SearchSuggestionsProvider> {
  private final MembersInjector<ContentProvider> supertypeInjector;
  private final Provider<InformationManager> informationManagerProvider;

  public SearchSuggestionsProvider_MembersInjector(MembersInjector<ContentProvider> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
    assert supertypeInjector != null;
    this.supertypeInjector = supertypeInjector;
    assert informationManagerProvider != null;
    this.informationManagerProvider = informationManagerProvider;
  }

  @Override
  public void injectMembers(SearchSuggestionsProvider instance) {  
    if (instance == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    supertypeInjector.injectMembers(instance);
    instance.informationManager = informationManagerProvider.get();
  }

  public static MembersInjector<SearchSuggestionsProvider> create(MembersInjector<ContentProvider> supertypeInjector, Provider<InformationManager> informationManagerProvider) {  
      return new SearchSuggestionsProvider_MembersInjector(supertypeInjector, informationManagerProvider);
  }
}


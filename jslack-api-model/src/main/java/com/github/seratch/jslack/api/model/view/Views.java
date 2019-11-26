package com.github.seratch.jslack.api.model.view;

import com.github.seratch.jslack.api.model.ModelConfigurator;

public class Views {

    private Views() {}

    public static View view(ModelConfigurator<View.ViewBuilder> configurator) {
        return configurator.configure(View.builder()).build();
    }

    public static ViewClose viewClose(ModelConfigurator<ViewClose.ViewCloseBuilder> configurator) {
        return configurator.configure(ViewClose.builder()).build();
    }

    public static ViewState viewState(ModelConfigurator<ViewState.ViewStateBuilder> configurator) {
        return configurator.configure(ViewState.builder()).build();
    }

    public static ViewSubmit viewSubmit(ModelConfigurator<ViewSubmit.ViewSubmitBuilder> configurator) {
        return configurator.configure(ViewSubmit.builder()).build();
    }

    public static ViewTitle viewTitle(ModelConfigurator<ViewTitle.ViewTitleBuilder> configurator) {
        return configurator.configure(ViewTitle.builder()).build();
    }

}

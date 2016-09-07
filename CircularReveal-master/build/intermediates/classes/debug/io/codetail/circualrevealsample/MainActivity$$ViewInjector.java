// Generated code from Butter Knife. Do not modify!
package io.codetail.circualrevealsample;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$$ViewInjector {
  public static void inject(Finder finder, final io.codetail.circualrevealsample.MainActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427436, "field 'mToolbar'");
    target.mToolbar = (android.support.v7.widget.Toolbar) view;
    view = finder.findRequiredView(source, 2131427435, "field 'mFloatingButton'");
    target.mFloatingButton = (android.support.design.widget.FloatingActionButton) view;
    view = finder.findRequiredView(source, 2131427434, "field 'mCardsGroup'");
    target.mCardsGroup = (android.support.v7.widget.RecyclerView) view;
  }

  public static void reset(io.codetail.circualrevealsample.MainActivity target) {
    target.mToolbar = null;
    target.mFloatingButton = null;
    target.mCardsGroup = null;
  }
}

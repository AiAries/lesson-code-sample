// Generated code from Butter Knife. Do not modify!
package io.codetail.circualrevealsample;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MainActivity$CardHolder$$ViewInjector {
  public static void inject(Finder finder, final io.codetail.circualrevealsample.MainActivity.CardHolder target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131427440, "field 'mCard'");
    target.mCard = (android.support.v7.widget.CardView) view;
  }

  public static void reset(io.codetail.circualrevealsample.MainActivity.CardHolder target) {
    target.mCard = null;
  }
}

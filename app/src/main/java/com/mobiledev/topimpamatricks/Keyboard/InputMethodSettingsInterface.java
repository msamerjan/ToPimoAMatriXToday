package com.mobiledev.topimpamatricks.Keyboard;

import android.graphics.drawable.Drawable;

/**
 * Created by maiaphoebedylansamerjan on 4/29/16.
 */
public interface InputMethodSettingsInterface {
    public void setInputMethodSettingsCategoryTitle(int resId);
    /**
     * Sets the title for the input method settings category with a CharSequence.
     * @param title The title for this preference.
     */
    public void setInputMethodSettingsCategoryTitle(CharSequence title);
    /**
     * Sets the title for the input method enabler preference for launching subtype enabler with a
     * resource ID.
     * @param resId The resource ID of the title.
     */
    public void setSubtypeEnablerTitle(int resId);
    /**
     * Sets the title for the input method enabler preference for launching subtype enabler with a
     * CharSequence.
     * @param title The title for this preference.
     */
    public void setSubtypeEnablerTitle(CharSequence title);
    /**
     * Sets the icon for the preference for launching subtype enabler with a resource ID.
     * @param resId The resource id of an optional icon for the preference.
     */
    public void setSubtypeEnablerIcon(int resId);
    /**
     * Sets the icon for the Preference for launching subtype enabler with a Drawable.
     * @param drawable The drawable of an optional icon for the preference.
     */
    public void setSubtypeEnablerIcon(Drawable drawable);
}

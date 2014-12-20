package com.jimulabs.mirroranimator;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.jimulabs.mirror.model.ResRef;

/**
 * Created by lintonye on 14-12-16.
 */
public abstract class MirrorAnimatorScript {
    private static final String LOG_TAG = "MirrorAnimatorScript";

    private final View mRootView;
    private final Context mContext;

    public MirrorAnimatorScript(Context context, View root) {
        mContext = context!=null ? context : root.getContext();
        mRootView = root;
        setGlobalSpeed(1);
    }

    public MirrorAnimatorScript(View root) {
        this(null, root);
    }

    public static class InvalidViewRefException extends RuntimeException {
        public InvalidViewRefException(String ref) {
            super(String.format("Invalid view ref:\"%s\"", ref));
        }
    }

    public static class CannotFindViewException extends RuntimeException {
        public final String ref;

        public CannotFindViewException(String ref) {
            super(ref);
            this.ref = ref;
        }
    }

    protected MirrorView $(String ref) {
        ResRef resRef = ResRef.parseResourceRef(ref, ResRef.Type.id);
        int id = resolveResourceId(mContext, resRef);
        if (id == 0) {
            throw new InvalidViewRefException(ref);
        } else {
            View view = mRootView.findViewById(id);
            if (view != null) {
                return new MirrorView(view);
            } else {
                throw new CannotFindViewException(ref);
            }
        }
    }

    protected MirrorAnimator tg(MirrorAnimator... animators) {
        return together(animators);
    }

    private MirrorAnimator together(MirrorAnimator... animators) {
        return AnimatorUtils.together(animators);
    }

    protected MirrorAnimator sq(MirrorAnimator... animators) {
        return sequence(animators);
    }

    private MirrorAnimator sequence(MirrorAnimator... animators) {
        return AnimatorUtils.sequence(animators);
    }

    public void setGlobalSpeed(double speed) {
        AnimatorUtils.setGlobalSpeed(speed);
    }

    protected abstract void enterSandbox();

    public void runEditModeScript() {
        enterSandbox();
    }

    public static int resolveResourceId(Context context, ResRef ref) {
        if (ref == null) {
            return 0;
        } else {
            return resolveResourceId(context, ref.name, ref.type, ref.isAndroidInternal);
        }
    }

    public static int resolveResourceId(Context context, String name, ResRef.Type type, boolean isAndroidInternal) {
        String packageName = isAndroidInternal ? "android" : context.getPackageName();
        int id = context.getResources().getIdentifier(name, type.name(), packageName);
        if (id == 0) {
            Log.d(LOG_TAG, String.format("Failed to find (id=0): name=%s, type=%s, package=%s",
                    name, type, packageName));
        }
        return id;
    }
}

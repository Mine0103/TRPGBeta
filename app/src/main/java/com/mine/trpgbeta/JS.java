/*
 * Create by mine on 2020. 10. 14.
 * Copyright (c) 2020. mine. All rights reserved.
 *
 */

package com.mine.trpgbeta;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class JS extends AppCompatActivity {
    Context rhino;
    Scriptable scope;
    variable var = (variable)getApplication();
    private void toast(String msg, boolean isLong) {
        if(isLong) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
    public void runJS(String sourcs) {
        rhino = Context.enter();
        rhino.setOptimizationLevel(-1);
        try {
            scope = rhino.initStandardObjects();
            ScriptableObject.putProperty(scope, "ctx", this);
            ScriptableObject.defineClass(scope, TRPG.class);
            rhino.evaluateString(scope, sourcs, "JavaScript", 1, null);
            callScriptMethod("onStart", new Object[]{this});
        } catch (Exception e) {
            toast(e.toString(), true);
        } finally {
            Context.exit();
        }
    }

    public void callScriptMethod(String name, Object[] args) {
        try {
            Function func = (Function)scope.get(name, scope);
            func.call(rhino, scope, scope, args);
        } catch (ClassCastException e) {
            //null
        } catch (Exception e) {
            toast(e.toString(), true);
        }
    }

    public static class TRPG extends ScriptableObject {

        @Override
        public String getClassName() {
            return "TRPG";
        }

        @JSStaticFunction
        public static String getName() {
            return "TRPG";
        }
    }
}

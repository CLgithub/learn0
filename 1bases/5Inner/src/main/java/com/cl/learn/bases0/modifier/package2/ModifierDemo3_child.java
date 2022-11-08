package com.cl.learn.bases0.modifier.package2;

import com.cl.learn.bases0.modifier.package1.ModifierDemo1_class;

/**
 *
 */
public class ModifierDemo3_child extends ModifierDemo1_class {
    public static void main(String[] args) {
        ModifierDemo1_class modifierDemo1_class = new ModifierDemo1_class();
        System.out.println(modifierDemo1_class.public_);
        System.out.println(ModifierDemo1_class.protected_static);

        ModifierDemo3_child modifierDemo3_child= new ModifierDemo3_child();
        System.out.println(modifierDemo3_child.public_);
        System.out.println(modifierDemo3_child.protected_);
        System.out.println(ModifierDemo3_child.protected_static);

    }
}

package com.zrzhao.gateway.registry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zrzhao
 * @date 2021/1/23
 */
public class Registry {

    private final List<String> registered = new ArrayList<>();
    private final Set<String> registeredSet = new HashSet<>();

    private Registry() {
    }

    private static class RegistryHolder {
        private static final Registry REGISTRY = new Registry();
    }

    public static Registry getInstance() {
        return RegistryHolder.REGISTRY;
    }

    public Registry register(String url) {
        if (registeredSet.contains(url)) {
            return this;
        }
        this.registeredSet.add(url);
        this.registered.add(url);
        return this;
    }

    public List<String> getRegistered() {
        return registered;
    }
}

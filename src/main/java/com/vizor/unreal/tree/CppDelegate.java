/*
 * Copyright 2018 Vizor Games LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
 
/*
 * Modified 2021 by Singularity 6, Inc.
 */
 
package com.vizor.unreal.tree;

import com.vizor.unreal.writer.CppPrinter;

import java.util.List;

import static java.util.Collections.unmodifiableList;

public final class CppDelegate implements CtLeaf
{
    private final boolean isDynamicDelegate;
    private final boolean isMulticastDelegate;
    private final CppType type;
    private final List<CppArgument> arguments;

    public CppDelegate(final boolean isDynamicDelegate, final boolean isMulticastDelegate, final CppType type, final List<CppArgument> arguments)
    {
        this.isDynamicDelegate = isDynamicDelegate;
        this.isMulticastDelegate = isMulticastDelegate;
        this.type = type;
        this.arguments = unmodifiableList(arguments);
    }

    public final String getDelegateMacroStringBase()
    {
        if (isDynamicDelegate)
        {
            if (isMulticastDelegate)
            {
                return "DECLARE_DYNAMIC_MULTICAST_DELEGATE";
            }
            else
            {
                return "DECLARE_DYNAMIC_DELEGATE";
            }
        }
        else
        {
            if (isMulticastDelegate)
            {
                return "DECLARE_MULTICAST_DELEGATE";
            }
            else
            {
                return "DECLARE_DELEGATE";
            }
        }
    }

    public String getTense()
    {
        switch (getArguments().size())
        {
            case 0: return "";
            case 1: return "OneParam";
            case 2: return "TwoParams";
            case 3: return "ThreeParams";
            case 4: return "FourParams";
            case 5: return "FiveParams";
            case 6: return "SixParams";
            case 7: return "SevenParams";
            case 8: return "EightParams";
            case 9: return "NineParams";
        }

        throw new RuntimeException("A tense for '" + getArguments().size() + "' arguments wasn't found");
    }

    public final List<CppArgument> getArguments()
    {
        return arguments;
    }

    public final CppType getType()
    {
        return type;
    }

    @Override
    public CppPrinter accept(CppPrinter printer)
    {
        printer.visit(this);
        return printer;
    }

    public final boolean isDynamicDelegate() {
        return isDynamicDelegate;
    }

    public final boolean isMulticastDelegate() {
        return isMulticastDelegate;
    }
}

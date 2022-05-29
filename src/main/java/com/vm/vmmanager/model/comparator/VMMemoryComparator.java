package com.vm.vmmanager.model.comparator;


import com.vm.vmmanager.model.VMDao;

import java.util.Comparator;

public class VMMemoryComparator implements Comparator<VMDao> {

    @Override
    public int compare(VMDao o1, VMDao o2) {
        if (o1.getRamSizeInGB() > o2.getRamSizeInGB())
            return -1;
        else if (o1.getRamSizeInGB() < o2.getRamSizeInGB())
            return 1;
        else
            return 0;
    }
}

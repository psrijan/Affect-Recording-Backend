// created by Srijan Pandey (sp3557@drexel.edu) at 20210305 23:04.
package com.cheney.careu.backend.dto;

public enum Activity {
    Walking(1),
    Running(2),
    Sleeping(3),
    Eating(4),
    Other(20);

    final long index;
    Activity(long index)  {
       this.index= index;
    }

    public static Activity valueOf(Long i) {
        for (Activity e: values()) {
            if (e.index == i)
                return e;
        }
        return Other;
    }
}

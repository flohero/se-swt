module swt.fhbay.dal {
    requires java.persistence;
    exports swt6.fhbay.util;
    exports swt6.fhbay.repositories.impl;
    exports swt6.fhbay.domain;
    opens swt6.fhbay.domain;
    exports swt6.fhbay.repositories.impl.base;
}
package com.saurabh.E_Commerce.models.enums;

public enum PermissionEnum {
    //products
    CREATE_PRODUCT,
    UPDATE_PRODUCT,
    DELETE_PRODUCT,
    VIEW_PRODUCT,

    //inventory
    VIEW_INVENTORY,
    UPDATE_INVENTORY,
    ADJUST_STOCK,

    //ORDERS
    VIEW_ORDERS,
    MANAGE_ORDERS,
    CANCEL_ORDERS,

    //payment
    VIEW_PAYMENTS,
    REFUND_PAYMENT,

    //users
    VIEW_USER,
    UPDATE_USER,
    DELETE_USER,

    //categories
    CREATE_CATEGORY,
    VIEW_CATEGORY,
    UPDATE_CATEGORY,
    DELETE_CATEGORY,

    //roles
    MANAGE_ROLES,
    ASSIGN_PERMISSIONS,


}

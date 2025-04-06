package com.datmt.learning.java.common.helper;


public final class MessagingTopics {

    private MessagingTopics() {
    } // Prevent instantiation

    // ==========================
    // ORDER DOMAIN
    // ==========================
    public static final class Order {
        public static final String EXCHANGE = "order.exchange";

        public static final String ROUTING_KEY_ORDER_PLACED = "order.placed";
        public static final String ROUTING_KEY_ORDER_SHIPPED = "order.shipped";
        public static final String ROUTING_KEY_ORDER_FAILED = "order.failed";

        public static final String QUEUE_PAYMENT_ORDER_PLACED = "payment.order_placed";
        public static final String QUEUE_INVENTORY_ORDER_PLACED = "inventory.order_placed";
    }

    // ==========================
    // PAYMENT DOMAIN
    // ==========================
    public static final class Payment {
        public static final String EXCHANGE = "payment.exchange";

        public static final String ROUTING_KEY_PAYMENT_PROCESSED = "payment.processed";
        public static final String ROUTING_KEY_PAYMENT_FAILED = "payment.failed";

        public static final String QUEUE_ORDER_PAYMENT_PROCESSED = "order.payment_processed";
        public static final String QUEUE_SHIPMENT_PAYMENT_PROCESSED = "shipment.payment_processed";
        public static final String QUEUE_ORDER_PAYMENT_FAILED = "order.payment_failed";
    }

    // ==========================
    // INVENTORY DOMAIN
    // ==========================
    public static final class Inventory {
        public static final String EXCHANGE = "inventory.exchange";

        public static final String ROUTING_KEY_INVENTORY_RESERVED = "inventory.reserved";
        public static final String ROUTING_KEY_OUT_OF_STOCK = "inventory.out_of_stock";

        public static final String QUEUE_ORDER_INVENTORY_RESERVED = "order.inventory_reserved";
        public static final String QUEUE_ORDER_OUT_OF_STOCK = "order.inventory_out_of_stock";
    }

    // ==========================
    // SHIPMENT DOMAIN
    // ==========================
    public static final class Shipment {
        public static final String EXCHANGE = "shipment.exchange";

        public static final String ROUTING_KEY_SHIPMENT_CREATED = "shipment.created";
        public static final String ROUTING_KEY_SHIPMENT_DELIVERED = "shipment.delivered";

        public static final String QUEUE_ORDER_SHIPMENT_CREATED = "order.shipment_created";
    }

    // ==========================
    // CATALOG DOMAIN
    // ==========================
    public static final class Catalog {
        public static final String EXCHANGE = "catalog.exchange";

        public static final String ROUTING_KEY_PRODUCT_UPDATED = "product.updated";

        public static final String QUEUE_ORDER_PRODUCT_UPDATED = "order.product_updated";
    }
}

package food_delivery_app_backend.Enum;

public enum OrderStatus {
    CREATED,
    ACCEPTED_BY_RESTAURANT,
    REJECTED_BY_RESTAURANT,
    DRIVER_ASSIGNED,
    PICKED_UP,
    DELIVERED,
    CANCEL,
    FINDING_DELIVERY_PARTNER
}

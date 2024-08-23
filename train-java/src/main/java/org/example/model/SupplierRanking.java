package org.example.model;

import java.util.UUID;

public record SupplierRanking(UUID receiverAccountID, long transactionCount) {
}
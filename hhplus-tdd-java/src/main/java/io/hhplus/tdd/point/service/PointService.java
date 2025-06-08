package io.hhplus.tdd.point.service;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.entity.PointHistory;
import io.hhplus.tdd.point.entity.UserPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserPointTable userPointTable;
    private final PointHistoryTable pointHistoryTable;

    public UserPoint readPoint(long userId) {

        return userPointTable.selectById(userId);
    }

    public UserPoint chargePoint(long userId, long point) {
        UserPoint userPoint = readPoint(userId);
        UserPoint chargeUserPoint = updatePoint(userPoint, point);
        addPointHistory(userId, point, TransactionType.CHARGE);
        return chargeUserPoint;
    }
    public UserPoint updatePoint(UserPoint userPoint, long point) {
        long updatePoint = userPoint.addPoint(point);
        return userPointTable.insertOrUpdate(userPoint.id(), updatePoint);
    }

    public UserPoint usePoint(long userId, long point) {
        UserPoint userPoint = readPoint(userId);
        UserPoint useUserPoint = updatePoint(userPoint, -point);
        addPointHistory(userId, point, TransactionType.USE);
        return useUserPoint;
    }
    public List<PointHistory> readPointHistories(long userId) {

        return pointHistoryTable.selectAllByUserId(userId).stream()
                .sorted(Comparator.comparing(PointHistory::updateMillis).reversed())
                .toList();
    }

    public void addPointHistory(long userId, long amount, TransactionType type) {
        pointHistoryTable.insert(userId, amount, type, System.currentTimeMillis());
    }
}

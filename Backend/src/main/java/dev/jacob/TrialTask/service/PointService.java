package dev.jacob.TrialTask.service;

import dev.jacob.TrialTask.api.model.PointModel;
import dev.jacob.TrialTask.api.model.request.PointCreationRequest;
import dev.jacob.TrialTask.api.model.request.PointEditRequest;
import dev.jacob.TrialTask.storage.entity.Point;
import dev.jacob.TrialTask.storage.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointService {

    private final PointRepository pointRepository;

    @Autowired
    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public List<PointModel> getAllPoints() {

        return pointRepository.findAll()
                .stream()
                .map(point -> new PointModel(point.getId(), point.getName(), point.getX(), point.getY())).toList();
    }

    public PointModel getPointById(long id) {

        Point point = pointRepository.getReferenceById(id);

        return new PointModel(point.getId(), point.getName(), point.getX(), point.getY());
    }


    public PointModel addPoint(PointCreationRequest pointReq) {

        Point point = new Point(pointReq.getName(), pointReq.getX(), pointReq.getY());
        point = pointRepository.save(point);

        return new PointModel(point.getId(), point.getName(), point.getX(), point.getY());
    }

    public boolean deletePoint(String name) {

        Point point = pointRepository.findByName(name);

        if(point == null) {
            return false;
        }

        pointRepository.delete(point);
        return true;

    }


    public PointModel editPoint(String name, PointEditRequest pointReq) {

        Point point = pointRepository.findByName(name);

        if(point == null) {
            return null;
        }

        if(pointReq.getX() != null) {
            point.setX(pointReq.getX());
        }
        if(pointReq.getY() != null) {
            point.setY(pointReq.getY());
        }
        if(pointReq.getName() != null) {
            point.setName(pointReq.getName());
        }
        point = pointRepository.save(point);

        return new PointModel(point.getId(), point.getName(), point.getX(), point.getY());
    }
}

package dev.jacob.TrialTask.api.controller;

import dev.jacob.TrialTask.api.model.PointModel;
import dev.jacob.TrialTask.api.model.request.PointCreationRequest;
import dev.jacob.TrialTask.api.model.request.PointEditRequest;
import dev.jacob.TrialTask.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/points")
    public ResponseEntity<List<PointModel>> getPoints() {
        return ResponseEntity.ok()
                .body(pointService.getAllPoints());
    }

    @PostMapping("/addPoint")
    public ResponseEntity<PointModel> addPoint(@RequestBody PointCreationRequest pointReq) {
        PointModel result = pointService.addPoint(pointReq);
        if(result == null) {
            return ResponseEntity.badRequest().body(null);
        }
        return ResponseEntity.status(201).body(result);
    }

    @DeleteMapping("/deletePoint/{name}")
    public ResponseEntity<Void> deletePoint(@PathVariable String name) {
        boolean success = pointService.deletePoint(name);
        if (success) {
            return ResponseEntity.status(202).build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/editPoint/{name}")
    public ResponseEntity<PointModel> editPoint(@PathVariable String name, @RequestBody PointEditRequest pointReq) {
        PointModel point = pointService.editPoint(name, pointReq);
        if(point != null) {
            return ResponseEntity.status(200).body(point);
        }
        return ResponseEntity.status(404).build();
    }

    @GetMapping("/findClosest/{name}")
    public ResponseEntity<List<PointModel>> findClosest(@PathVariable String name) {
        //TODO implement method if needed
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/findFurthest/{name}")
    public ResponseEntity<List<PointModel>> findFurthest(@PathVariable String name) {
        //TODO implement method if needed
        return ResponseEntity.status(500).build();
    }
}

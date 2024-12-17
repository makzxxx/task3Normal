package ru.vsu.cs.cg.g12.chernyshev_m_a.normals;

import ru.vsu.cs.cg.g12.chernyshev_m_a.math.Vector3f;
import ru.vsu.cs.cg.g12.chernyshev_m_a.model.Model;
import ru.vsu.cs.cg.g12.chernyshev_m_a.model.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NormalCalculator {

    public static void calculateNormals(Model model) {
        List<Vector3f> computedNormals = new ArrayList<>();
        Map<Integer, List<Vector3f>> vertexToNormals = new HashMap<>();

        for (Polygon polygon : model.polygons) {
            List<Integer> vertexIndices = polygon.getVertexIndices();

            for (int i = 0; i < vertexIndices.size() - 2; i++) {
                Vector3f v0 = model.vertices.get(vertexIndices.get(0));
                Vector3f v1 = model.vertices.get(vertexIndices.get(i + 1));
                Vector3f v2 = model.vertices.get(vertexIndices.get(i + 2));

                Vector3f edge1 = subtract(v1, v0);
                Vector3f edge2 = subtract(v2, v0);
                Vector3f normal = crossProduct(edge1, edge2).normal();

                computedNormals.add(normal);

                for (int index : List.of(vertexIndices.get(0), vertexIndices.get(i + 1), vertexIndices.get(i + 2))) {
                    vertexToNormals.computeIfAbsent(index, k -> new ArrayList<>()).add(normal);
                }
            }
        }

        model.normals.clear();
        for (int i = 0; i < model.vertices.size(); i++) {
            List<Vector3f> normals = vertexToNormals.getOrDefault(i, new ArrayList<>());
            Vector3f averageNormal = average(normals);
            model.normals.add(averageNormal);
        }
    }

    private static Vector3f subtract(Vector3f v1, Vector3f v2) {
        return new Vector3f(
                v1.x - v2.x,
                v1.y - v2.y,
                v1.z - v2.z
        );
    }

    private static Vector3f crossProduct(Vector3f v1, Vector3f v2) {
        return new Vector3f(
                v1.y * v2.z - v1.z * v2.y,
                v1.z * v2.x - v1.x * v2.z,
                v1.x * v2.y - v1.y * v2.x
        );
    }

    private static Vector3f average(List<Vector3f> vectors) {
        if (vectors.isEmpty()) {
            return new Vector3f(0, 0, 0);
        }

        float x = 0, y = 0, z = 0;
        for (Vector3f vector : vectors) {
            x += vector.x;
            y += vector.y;
            z += vector.z;
        }
        int count = vectors.size();
        return new Vector3f(x / count, y / count, z / count).normal();
    }
}
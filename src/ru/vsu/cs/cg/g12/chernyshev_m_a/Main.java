package ru.vsu.cs.cg.g12.chernyshev_m_a;

import ru.vsu.cs.cg.g12.chernyshev_m_a.normals.NormalCalculator;
import ru.vsu.cs.cg.g12.chernyshev_m_a.model.Model;
import ru.vsu.cs.cg.g12.chernyshev_m_a.obj_reader.ObjReader;
import ru.vsu.cs.cg.g12.chernyshev_m_a.obj_writer.ObjWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        String fileContent = Files.readString(Path.of("C:\\Users\\maksi\\IdeaProjects\\task3Normal\\models\\after\\TeapotNoUV.obj"));

        Model model = ObjReader.read(fileContent);
        ObjWriter writerClass = new ObjWriter();
        NormalCalculator.calculateNormals(model);

        writerClass.write(model,"C:\\Users\\maksi\\IdeaProjects\\task3Normal\\models\\Normals\\TeapotNoUV.obj");
    }


}

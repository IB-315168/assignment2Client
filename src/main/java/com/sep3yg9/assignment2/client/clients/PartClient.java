package com.sep3yg9.assignment2.client.clients;

import com.google.protobuf.Empty;
import com.sep3yg9.assignment2.client.Main;
import com.sep3yg9.assignment2.grpc.protobuf.parts.Part;
import com.sep3yg9.assignment2.grpc.protobuf.parts.PartList;
import com.sep3yg9.assignment2.grpc.protobuf.parts.PartServiceGrpc;
import io.grpc.ManagedChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PartClient
{
  private final Scanner scanner = new Scanner(System.in);
  private final List<String> partOptions = new ArrayList<>(List.of(
      "1", "2", "0"
  ));

  private final PartServiceGrpc.PartServiceBlockingStub stub;

  public PartClient(ManagedChannel channel) {
     this.stub = PartServiceGrpc.newBlockingStub(channel);
  }

  public void partManagement() {
    String option = "";

    do {
      System.out.println("Select option:");
      System.out.println("1 - Create part");
      System.out.println("2 - View all remaining parts");
      System.out.println("0 - Back to main menu");
      System.out.print(": ");
      option = scanner.nextLine();
    } while (!partOptions.contains(option));

    switch (option) {
      case "1": {
        createPart();
      }
      case "2": {
        getAllRemainingParts();
      }
      case "0": {
        Main.mainMenu();
      }
    }
  }

  public void createPart() {
    System.out.println("------------------------------------------");
    System.out.println("Part creation:");
    System.out.println("------------------------------------------");

    System.out.println("Type in parts weight [kg]: ");
    double weight = Double.parseDouble(scanner.nextLine());

    System.out.println("Type in animal registration number: ");
    int animalId = Integer.parseInt(scanner.nextLine());

    System.out.println("Type in part type: ");
    String type = scanner.nextLine();

    Part partCreated = stub.createPart(Part.newBuilder().setAnimalId(animalId).setWeight(weight).setType(type).build());
    System.out.println("------------------------------------------");
    System.out.println(partCreated + "\n");
    partManagement();
  }

  public void getAllRemainingParts() {
    System.out.println("------------------------------------------");
    System.out.println("Remaining, created parts");
    System.out.println("------------------------------------------");
    PartList partList = stub.getAllRemainingParts(Empty.newBuilder().build());
    System.out.println(partList + "\n");
    partManagement();
  }
}

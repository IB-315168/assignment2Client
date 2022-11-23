package client.clients;

import com.google.protobuf.Empty;
import com.google.protobuf.Int64Value;
import com.sep3yg9.assignment2.client.Main;
import com.sep3yg9.assignment2.grpc.protobuf.parts.PartTray;
import com.sep3yg9.assignment2.grpc.protobuf.trays.Tray;
import com.sep3yg9.assignment2.grpc.protobuf.trays.TrayList;
import com.sep3yg9.assignment2.grpc.protobuf.trays.TrayServiceGrpc;
import io.grpc.ManagedChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrayClient
{
  private final Scanner scanner = new Scanner(System.in);
  private final List<String> trayOptions = new ArrayList<>(List.of(
      "1", "2", "3", "4", "0"
  ));

  private final TrayServiceGrpc.TrayServiceBlockingStub stub;

  public TrayClient(ManagedChannel channel)
  {
    this.stub = TrayServiceGrpc.newBlockingStub(channel);
  }

  public void trayManagement() {
    String option = "";

    do {
      System.out.println("Select option:");
      System.out.println("1 - Create tray");
      System.out.println("2 - Put part on tray");
      System.out.println("3 - Mark tray as finished");
      System.out.println("4 - Get all trays");
      System.out.println("0 - Back to main menu");
      System.out.print(": ");
      option = scanner.nextLine();
    } while (!trayOptions.contains(option));

    switch (option) {
      case "1": {
        createTray();
      }
      case "2": {
        putPartOnTray();
      }
      case "3" : {
        markAsFinished();
      }
      case "4": {
        getAllTrays();
      }
      case "0": {
        Main.mainMenu();
      }
    }
  }

  public void createTray() {
    System.out.println("------------------------------------------");
    System.out.println("Tray creation:");
    System.out.println("------------------------------------------");

    System.out.println("Type in trays max weight [kg]: ");
    double maxWeight = Double.parseDouble(scanner.nextLine());

    System.out.println("Type in part type for tray: ");
    String type = scanner.nextLine();

    Tray trayCreated = stub.createTray(Tray.newBuilder().setMaxWeight(maxWeight).setType(type).build());
    System.out.println("------------------------------------------");
    System.out.println(trayCreated + "\n");
    trayManagement();
  }

  public void putPartOnTray() {
    System.out.println("------------------------------------------");
    System.out.println("Put part on tray:");
    System.out.println("------------------------------------------");

    System.out.println("Type in tray id: ");
    long trayId = Long.parseLong(scanner.nextLine());

    System.out.println("Type in part id: ");
    long partId = Long.parseLong(scanner.nextLine());

    Tray tray = stub.putOnTray(PartTray.newBuilder().setTrayId(trayId).setPartId(partId).build());
    System.out.println("------------------------------------------");
    System.out.println(tray + "\n");
    trayManagement();
  }

  public void markAsFinished() {
    System.out.println("------------------------------------------");
    System.out.println("Mark tray as finished:");
    System.out.println("------------------------------------------");

    System.out.println("Type in tray id: ");
    long trayId = Long.parseLong(scanner.nextLine());

    Tray tray = stub.trayFinished(Int64Value.newBuilder().setValue(trayId).build());
    System.out.println("------------------------------------------");
    System.out.println(tray + "\n");
    trayManagement();
  }

  public void getAllTrays() {
    System.out.println("------------------------------------------");
    System.out.println("All trays and their contents:");
    System.out.println("------------------------------------------");
    TrayList trayList = stub.getAllTrays(Empty.newBuilder().build());
    System.out.println(trayList + "\n");
    trayManagement();
  }
}

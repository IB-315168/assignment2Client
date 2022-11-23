package com.sep3yg9.assignment2.client;

import com.sep3yg9.assignment2.client.clients.HistoryClient;
import com.sep3yg9.assignment2.client.clients.PartClient;
import com.sep3yg9.assignment2.client.clients.ProductClient;
import com.sep3yg9.assignment2.client.clients.TrayClient;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main
{
  private static final Scanner scanner = new Scanner(System.in);
  private final static List<String> mainOptions = new ArrayList<>(List.of(
      "0", "1", "2", "3", "4"
  ));
  private static final ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 6565)
      .usePlaintext()
      .build();
  private static final PartClient partClient = new PartClient(channel);
  private static final TrayClient trayClient = new TrayClient(channel);
  private static final ProductClient productClient = new ProductClient(channel);
  private static final HistoryClient historyClient = new HistoryClient(channel);

  public static void main(String[] args)
  {
    mainMenu();
  }

  public static void mainMenu() {
    String option = "";

    do {
      System.out.println("Select option to continue:");
      System.out.println("1 - Part management");
      System.out.println("2 - Tray management");
      System.out.println("3 - Product management");
      System.out.println("4 - View history");
      System.out.println("0 - Exit application");
      System.out.print(": ");
      option = scanner.nextLine();
    } while (!mainOptions.contains(option));

    switch (option) {
      case "1": {
        partClient.partManagement();
      }

      case "2": {
        trayClient.trayManagement();
      }

      case "3": {
        productClient.productManagement();
      }

      case "4": {
        historyClient.historyManagement();
      }

      case "0": {
        System.exit(0);
      }
    }
  }

}
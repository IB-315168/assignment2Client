package com.sep3yg9.assignment2.client.clients;

import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import com.sep3yg9.assignment2.client.Main;
import com.sep3yg9.assignment2.grpc.protobuf.history.HistoryServiceGrpc;
import com.sep3yg9.assignment2.grpc.protobuf.products.AnimalList;
import com.sep3yg9.assignment2.grpc.protobuf.products.Product;
import com.sep3yg9.assignment2.grpc.protobuf.products.ProductList;
import com.sep3yg9.assignment2.grpc.protobuf.products.ProductServiceGrpc;
import io.grpc.ManagedChannel;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HistoryClient
{
  private final Scanner scanner = new Scanner(System.in);
  private final List<String> historyOptions = new ArrayList<>(List.of(
      "1", "2", "0"
  ));

  private final HistoryServiceGrpc.HistoryServiceBlockingStub stub;

  public HistoryClient(ManagedChannel channel)
  {
    this.stub = HistoryServiceGrpc.newBlockingStub(channel);
  }

  public void historyManagement() {
    String option = "";

    do {
      System.out.println("Select option:");
      System.out.println("1 - Get animals involved in a product");
      System.out.println("2 - Get products involving parts of specific animal");
      System.out.println("0 - Back to main menu");
      System.out.print(": ");
      option = scanner.nextLine();
    } while (!historyOptions.contains(option));

    switch (option) {
      case "1": {
        getProductsAnimals();
      }
      case "2": {
        getAnimalsProducts();
      }
      case "0": {
        Main.mainMenu();
      }
    }
  }

  public void getProductsAnimals() {
    System.out.println("------------------------------------------");
    System.out.println("Get animals involved:");
    System.out.println("------------------------------------------");

    System.out.println("Type in products id: ");
    int id = Integer.parseInt(scanner.nextLine());

    AnimalList animalList = stub.getProductsAnimals(
        Int32Value.newBuilder().setValue(id).build());
    System.out.println("------------------------------------------");
    System.out.println("Animals for product " + id);
    System.out.println(animalList + "\n");
    historyManagement();
  }

  public void getAnimalsProducts() {
    System.out.println("------------------------------------------");
    System.out.println("Get products of animal:");
    System.out.println("------------------------------------------");

    System.out.println("Type in animals id: ");
    int id = Integer.parseInt(scanner.nextLine());

    ProductList productList = stub.getAnimalsProducts(Int32Value.newBuilder().setValue(id).build());
    System.out.println("------------------------------------------");
    System.out.println("Products of animal regNo: " + id);
    System.out.println(productList + "\n");
    historyManagement();
  }
}

package com.mb.demo.articulateDemo.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "id")
@Builder
public class Attendee {

  private Long id;

  private String firstName, lastName;

  private String address, city, state, zipCode;
  private String phoneNumber, emailAddress;

}

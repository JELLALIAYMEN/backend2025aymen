package com.DPC.spring.services;

import com.DPC.spring.entities.Sousclass;

import java.util.List;

public interface   Sousclassservice{
  public   Sousclass createSousclass(String nomssousclasse);
  public List<Sousclass> sousclasses();
}

Attribute VB_Name = "close"
Option Explicit

Function Ms_BscCloseComm(nCid As Integer) As Integer
     Dim rc As Integer
  
     'Cut the communications line.
     rc = BscDisConnect(nCid)
     'Close the port.
     rc = BscClose(nCid)
     Ms_BscCloseComm = rc
End Function

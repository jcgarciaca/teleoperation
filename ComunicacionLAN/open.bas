Attribute VB_Name = "open"
Option Explicit

'mode:  0...RS-232C   1...Ethernet
Function Ms_BscOpenComm(mode%) As Integer
     Dim nCid As Integer
     Dim rc As Integer
     Dim IPAddrress As String
     Ms_BscOpenComm = -1
     If mode = 0 Then
          'Open the port.
          nCid = BscOpen(CurDir$, 1)
          If nCid < 0 Then GoTo Ms_BscOpenComm_Exit
  
          'Set serial communications parameters.
          rc = BscSetCom(nCid, 1, 9600, 2, 8, 0)
     Else
          'Open the Ethernet line.
          nCid = BscOpen(CurDir$, PACKETETHERNET)
          If nCid < 0 Then GoTo Ms_BscOpenComm_Exit
  
          'Set Ethernet communications parameters.
          IPAddrress = "168.176.36.217"  '<---Specify any IP address.
          rc = BscSetEther(nCid, IPAddrress, 0, frmMain.hWnd)
     End If
          If rc <> 1 Then
          rc = BscClose(nCid)
          nCid = -1
          GoTo Ms_BscOpenComm_Exit
     End If
    
     'Connect communications line.
     rc = BscConnect(nCid)
     If rc <> 1 Then
          rc = BscClose(nCid)
          nCid = -1
          GoTo Ms_BscOpenComm_Exit
     End If
    
Ms_BscOpenComm_Exit:
     Ms_BscOpenComm = nCid
End Function

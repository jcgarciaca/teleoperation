VERSION 1.0 CLASS
BEGIN
  MultiUse = -1  'True
  Persistable = 0  'NotPersistable
  DataBindingBehavior = 0  'vbNone
  DataSourceBehavior  = 0  'vbNone
  MTSTransactionMode  = 0  'NotAnMTSObject
END
Attribute VB_Name = "CTest"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = True
Attribute VB_PredeclaredId = False
Attribute VB_Exposed = True
Option Explicit

Public nCid As Integer

Private Sub Class_Initialize()
  ' ********
End Sub

Public Function OpenPort() As Integer
     Dim mode As Integer
     
     mode = 1
     
     'MsgBox nCid
     
     nCid = Ms_BscOpenComm(mode)    ' mode=0 or 1
     'MsgBox nCid
     If nCid = 0 Then
          MsgBox "Opened Port"
     Else
          MsgBox "Open Port Error"
     End If
End Function


Public Function ClosePort() As Integer
     Dim rc As Integer
     rc = BscDisConnect(nCid)
     
     'MsgBox rc
     
     If rc = 1 Then
          MsgBox "Port Closed"
     Else
          rc = BscClose(nCid) 'BscEnforcedClose(0) 'BscDisConnect(0) 'Ms_BscCloseComm(0)
          If rc = 0 Then
              MsgBox "Port Closed"
          Else
              MsgBox "Port Closed Error"
          End If
     End If
End Function


Public Function SetPosition(ByVal posS As Double, ByVal posL As Double, ByVal posU As Double, ByVal posR As Double, ByVal posB As Double, ByVal posT As Double) As Integer
     Dim rc As Integer
     'Dim posS, posL, posR, posU, posB, posT As Integer
     Dim positions(0 To 11) As Double
     
     Dim speed As Integer
     speed = 5
     
     Dim ind As Integer
          
     For ind = 0 To 11
      positions(ind) = 0
     Next ind
     
     positions(0) = posS
     positions(1) = posL
     positions(2) = posU
     positions(3) = posR
     positions(4) = posB
     positions(5) = posT
     
     'MsgBox "Articulaciones"
     'MsgBox positions(0)
     'MsgBox positions(1)
     'MsgBox positions(2)
     'MsgBox positions(3)
     'MsgBox positions(4)
     'MsgBox positions(5)
     'MsgBox "Fin Articulaciones"
     
     If nCid <> -1 Then
          'Send position.
          rc = BscPMov(nCid, "MOVJ", "VJ", speed, 2, positions(0))
          
          'Confirms the return value.
          If rc = 0 Then
               'MsgBox "Position sent"
          Else
               MsgBox "Position transmission failure :" + Format$(rc)
          End If
          '-----------------------------------
       
          'No. of communication handlers.
          'rc = Ms_BscCloseComm(nCid)
     Else
          MsgBox "Cannot open the port."
     End If
End Function


Public Function ExecuteJob(ByVal jobname As String) As Integer
     Dim rc As Integer
     Dim ex As Integer
     
     'Check the specified job name.
     If jobname <> "" Then
          
          'It is checked whether the communication handler was able to be acquired.
          If nCid <> -1 Then

               'Delete job
               rc = BscSelectJob(nCid, jobname)
               rc = BscDeleteJob(nCid)

               'Transmit job.
               rc = BscSelectJob(nCid, jobname)
               'Confirms the return value.
               If rc = 0 Then
                    'Execute the job
                    ex = BscStartJob(nCid)
                    If ex = 0 Then
                        'MsgBox "Job executed"
                    Else
                        MsgBox "Job no executed"
                    End If
               Else
                    MsgBox "Job transmission failure :" + Format$(rc)
               End If
         Else
               MsgBox "Cannot open the port."
         End If
     Else
         MsgBox "Please specify the job name. "
     End If
End Function

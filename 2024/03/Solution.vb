Imports System.Text.RegularExpressions

Public Module Program
   Sub showMatch(ByVal text As String, ByVal expr As String)
      Console.WriteLine("The Expression: " + expr)
      Dim mc As MatchCollection = Regex.Matches(text, expr)
      Dim m As Match
      
      For Each m In mc
         Console.WriteLine(m.Value)
         Dim c As Capture
         
         For Each c In m.Captures
           Console.WriteLine(c.Value)
         Next c
      Next m
   End Sub
	Public Sub Main(args() As string)
    showMatch("mul(123,3)", "(mul\((\d{1,3}),(\d{1,3}\)))")
	End Sub
End Module
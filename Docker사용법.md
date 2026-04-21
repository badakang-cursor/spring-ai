# 로그인
 - 계정(hyogi.dev@gmail.com) : chamiseul == https://hub.docker.comm
 - docker login -u chamiseul
 - key : Docker hub > (우측상단)사용자 아이콘 > Account Settings > (좌측메뉴) Setting > Personal access tokens > 에서 생성한 토큰

# Dokerizing
 - docker build -t <이미지명>:[버전] .

# Docker Repository를 사용하지 않는 수동 작업(save/load, export/import) 
1. docker save (docker image -> tar)
   - docker 이미지를 tar파일로 저장하기 위해서는 docker save 커맨드를 사용한다.
   - docker save [옵션] <파일명> [이미지명]  
    `
    저장할 파일명을 지정하는 옵션은 -o 를 사용한다.   
    ex) docker save -o nginx.tar nginx:latest
    `

2. docker load (tar -> docker image)
   - tar파일로 만들어진 이미지를 다시 docker image로 되돌리기 위해서는 docker load 커맨드를 사용한다.
   - docker load -i tar파일명
 
3. docker export (docker container -> tar)
   - docker는 이미지 뿐 아니라 container를 tar파일로 저장하는 명령어를 제공한다.
   - docker export <컨테이너명 or 컨테이너ID> > xxx.tar
 
4. docker import (tar -> docker image)
   - export 커맨드를 통해 만들어진 tar 파일을 다시 docker image로 생성하는 명령어이다.
   - docker import <파일 or URL> - [image name[:tag name]]

<span style="color:#ffd33d">  
    ※ root 권한으로 실행하지 않을 경우, 액세스 권한이 없는 파일들이 포함되지 않는 문제가 발생할 수 있다.
</span>
<br/>
<br/>
<span style="color:#ffd33d">  
    (중요) export - import 와 save - load의 차이<br/>
      - docker export의 경우 컨테이너를 동작하는데 필요한 모든 파일이 압축된다. 즉, tar파일에 컨테이너의 루트 파일시스템 전체가 들어있는 것이다.<br/>
      - 반면에 docker save는 레이어 구조까지 포함한 형태로 압축이 된다.  즉, 기반이 되는 이미지가 같더라도 export와 save는 압축되는 파일 구조와 디렉터리가 다르다.<br/>
      - 결론은 export를 통해 생성한 tar 파일은 import로, save로 생성한 파일은 load로 이미지화 해야 한다.
</span>
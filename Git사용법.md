# 되돌리기
### reset
 - 특정시점으로 되돌리기 : 특정 시점 이후의 커밋은 제거됨
 - git reset --hard 해시코드 => 해시코드 시점으로 되돌림
   - hard : 완전 일치 시킴
   - mixed : 현재 파일 유지
   - soft : ?
 -  
 - git reset --hard => 로컬을 최종커밋된  버전으로 일치 시킴

### revert
 - 잘못된 작업 되돌리기(과거청산) : 생성은 삭제, 삭제는 복구, 수정은 미수정
 - 특정 시점을 복원해서 새로 신규로 커밋(특정시점과 새로운 커밋 사이의 커밋은 그대로 유지)
 - git revert 해시코드
   - revert 전 특정시점에 존재하는 파일이 수정된 상태이고 커밋 되지 않은 상태라면 해당 파일은 충돌 발생으로 revert 되지 않음
     1) 충돌 워닝 발생
     2) git rm "충돌파일명" => 삭제하고 자동 커밋함-로그남음
     3) git revert --continue => revert 계속 진행

### checkout
 - HEAD 변경
 - git checkout 해시코드 ==> 특정시점을 HEAD 로 지정
 - git switch main ==> 기본을 HEAD 로 지정
 - 
# Add(Stage) 및 커밋
 - Add
   - git add .
 - Commit 
   - git commit -m 'feet: 메시지'
 - Add 와 Commit
   - git commit -am 'feet: 메시지'

# Branch
 - branch 추가
   - git add 브랜치명
 - HEAD 변경
   - git switch 브랜치명
 - branch 추가 및 head 변경
   - git switch -c 브랜치명
 - branch 삭제
   - git branch -d 브랜치명
 - branch명 변경
   - git branch -m old브랜치명 new브랜치명
 - branch 강제 삭제
   1) 브랜치 생성 : new_branch
       - git branch new_branch
   2) new_branch 브랜치로 switch
       - git swith new_branch
   3) 파일수정 및 커밋
   4) main 으로 switch
       - git swith main
   5) 브랜치 삭제
       - git branch -d new_branch
   6) 브랜치 삭제 오류 발생
       - error: the branch 'new_branch' is not fully merged 
   7) 브랜치 강제 삭제
       - git branch -D new_branch
# 로그
 - git log
 - git log --all --decorate --oneline --graph

# Merge(병합) 및 Rebase(재배치-최종적으로는 커밋한 것으로 간주, 원래의 branch 기록 없어짐)

#### Merge(병합)
1. 주 브랜치(main)로 변경
    - git switch main
2. 병합
    - git merge 병합할브랜치명
    - VI 모드로 변경
    - :wq 로 저장
3. 병합하기 이전으로 변경 하려면 "병합할브랜치"의 최종 커밋한 해시코드로 가능
    - git reset "병합할브랜치의 최종 커밋한 해시코드"
    - git branch -d 병합할브랜치명

#### Rebase(재배치)
1. 재배치할 브랜치(new_branch)로 변경
    - git switch new_branch
2. 재배치
    - git rebase main
    - new_branch의 커밋 내역이 main의 최종 커밋된 이후 부터 커밋된 것으로 반영됨  
3. 커밋의 현재 위치가 rebase 직전에 위치하므로 현행화 시켜야함(past foward)
    - git switch new_branch
    - git merge new_branch ==> past forward
    - git branch -d new_branch

# Conflict(충돌)
#### Merge : 동일파일/동일라인이 수정되서 각각의 브랜치에 커밋됨
 1. conflict-1 브랜치 생성
    - git branch conflict-1
 2. main 브랜치에서 파일 수정 후 커밋
 3. conflict-1 브랜치에서 동일파일/동일라인 을 수정 후 커밋
    - 해결 1 - merge 중지 : git merge --abort
    - 해결 2 
      1) 기준되는 파일을 오픈하고 수정
      2) git add .
      3) git commit ==> 커밋메시지 자동 기입됨(Merge branch 'confict-1)
      4) :wq
 4. git branch -d conflict-1



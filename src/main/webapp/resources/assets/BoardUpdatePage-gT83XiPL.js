import{u as V,a as A,r as B,c as m,o as n,e as r,f as e,w as v,v as w,q as C,F as y,p as k,h as i,i as U,t as x}from"./index-p_1THz6I.js";import{b as p}from"./boardApi-BHqkWFcy.js";import"./index-DRcVQfPh.js";const M=e("h1",null,[e("i",{class:"fa-regular fa-pen-to-square"}),i(" 글 수정")],-1),S={class:"mb-3 mt-3 row"},D={class:"col-8"},L=e("label",{for:"title",class:"form-label"}," 제목 ",-1),N={class:"col-4"},R=e("label",{for:"title",class:"form-label"}," Type ",-1),E=["value"],I={class:"mb-3 mt-3"},P=e("label",{class:"form-label"}," 기존 첨부파일 ",-1),$=e("i",{class:"fa-solid fa-paperclip"},null,-1),j=["onClick"],z={class:"mb-3 mt-3"},G=e("label",{for:"files",class:"form-label"}," 첨부파일 ",-1),H={class:"mb-3 mt-3"},J=e("label",{for:"content",class:"form-label"}," 내용 ",-1),K=e("button",{type:"submit",class:"btn btn-primary me-3"},[e("i",{class:"fa-solid fa-check"}),i(" 확인 ")],-1),O=e("i",{class:"fa-solid fa-undo"},null,-1),Q=e("i",{class:"fa-solid fa-arrow-left"},null,-1),se={__name:"BoardUpdatePage",setup(W){const c=V(),f=A(),u=c.params.no,s=B({}),d=m([]),o=m({}),_=m(null),b=m([]),q=()=>{f.push({name:"board/detail",params:{no:u},query:c.query})},h=()=>{s.bno=o.value.bno,s.title=o.value.title,s.type=o.value.type,s.writer=o.value.writer,s.content=o.value.content,console.log(s)},F=async(a,l)=>{if(!confirm(l+"을 삭제할까요?"))return;await p.deleteAttachment(a);const t=d.value.findIndex(g=>g.fno===a);d.value.splice(t,1)},T=async()=>{if(!confirm("수정할까요?"))return;_.value.files.length>0&&(s.files=_.value.files),await p.update(s)!=null?(alert("게시글이 수정 되었습니다."),f.push({name:"board/detail",params:{no:u},query:c.query})):(alert("게시글이 수정에 실패 하였습니다."),f.push({name:"board/detail",params:{no:u},query:c.query}))};return(async()=>{const a=await p.get(u);o.value={...a},d.value=a.boardAttachFileList,h()})(),(async()=>{try{b.value=await p.getTypes(),console.log(b.value)}catch{}})(),(a,l)=>(n(),r(y,null,[M,e("form",{onSubmit:U(T,["prevent"])},[e("div",S,[e("div",D,[L,v(e("input",{type:"text",class:"form-control",placeholder:"제목",id:"title","onUpdate:modelValue":l[0]||(l[0]=t=>s.title=t)},null,512),[[w,s.title]])]),e("div",N,[R,v(e("select",{"onUpdate:modelValue":l[1]||(l[1]=t=>s.type=t),class:"form-select",required:""},[(n(!0),r(y,null,k(b.value,t=>(n(),r("option",{key:t,value:t.type},x(t.name),9,E))),128))],512),[[C,s.type]])])]),e("div",I,[P,(n(!0),r(y,null,k(d.value,t=>(n(),r("div",{key:t.fno,class:"attach"},[$,i(" "+x(t.originalFilename)+" ",1),e("i",{class:"fa-solid fa-trash-can text-danger ms-2",onClick:g=>F(t.fno,t.originalFilename)},null,8,j)]))),128))]),e("div",z,[G,e("input",{type:"file",class:"form-control",placeholder:"첨부파일",id:"files",ref_key:"files",ref:_,multiple:""},null,512)]),e("div",H,[J,v(e("textarea",{class:"form-control",placeholder:"내용",id:"content","onUpdate:modelValue":l[2]||(l[2]=t=>s.content=t),rows:"10"},null,512),[[w,s.content]])]),e("div",{class:"my-5 text-center"},[K,e("button",{type:"button",class:"btn btn-primary me-3",onClick:h},[O,i(" 취소 ")]),e("button",{class:"btn btn-primary",onClick:q},[Q,i(" 돌아가기 ")])])],32)],64))}};export{se as default};

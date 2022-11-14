import styled from "@emotion/styled";
import { useState } from "react";

const PopupContainer = styled.div`
  width: 320px;
  padding: 20px;
  background-color: #f3f3f3;
  font-family: Pretendard, serif;
`;

const Title = styled.h1`
  font-size: 20px;
  font-weight: bold;
  color: #000000;
  margin: 0;
`;

const RowContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-top: 16px;
`;

const TagContainer = styled(RowContainer)`
  flex-wrap: wrap;
  gap: 4px;
  justify-content: flex-start;
`;

const Label = styled.h2`
  font-size: 16px;
  font-weight: bold;
  color: #3c3c3c;
  margin: 0;
`;

const Input = styled.input`
  box-sizing: border-box;
  width: 250px;
  border-radius: 8px;
  border: thin solid #eeeeee;
  font-size: 16px;
  padding: 8px;
  background-color: #ffffff;
`;

const Select = styled.select`
  box-sizing: border-box;
  width: 250px;
  border-radius: 8px;
  border: thin solid #eeeeee;
  font-size: 16px;
  padding: 8px;
  background-color: #ffffff;
`;

const Tag = styled.div`
  box-sizing: border-box;
  width: fit-content;
  border-radius: 8px;
  font-size: 12px;
  padding: 8px;
  background-color: #d4d4d4;
`;

const TextArea = styled.textarea`
  box-sizing: border-box;
  width: 100%;
  min-height: 100px;
  resize: none;
  border-radius: 8px;
  font-size: 16px;
  padding: 8px;
  border: thin solid #eeeeee;
`;

const Button = styled.button`
  width: 100%;
  border-radius: 8px;
  border: thin solid #eeeeee;
  font-size: 16px;
  padding: 12px;
  background-color: #242830;
  color: #ffffff;
  font-weight: bold;
`;

const Popup = () => {
  const [name, setName] = useState("");
  const groups = [{ id: 1, name: "그룹1" }]; // TODO: state화 하고 API 연결
  const tags = []; // TODO: state화 하고 API 연결

  const send = (type: "add" | "panel") => {
    chrome.runtime.sendMessage({ type });
  };

  return (
    <PopupContainer>
      <Title>Add Bookmark</Title>
      <RowContainer>
        <Label>Name</Label>
        <Input onChange={(e) => setName(e.target.value)} value={name} />
      </RowContainer>
      <RowContainer>
        <Label>Group</Label>
        <Select>
          <option value={0} key={0} disabled selected>
            선택
          </option>
          {groups.map(({ id, name }) => (
            <option value={id} key={id}>
              {name}
            </option>
          ))}
        </Select>
      </RowContainer>
      <RowContainer>
        <Label>Tags</Label>
        <Input />
      </RowContainer>
      <TagContainer>
        {tags.map(({ id, name }) => (
          <Tag key={id}>{name}</Tag>
        ))}
      </TagContainer>
      <Label>Summary</Label>
      <RowContainer>
        <TextArea></TextArea>
      </RowContainer>
      <RowContainer>
        <Button onClick={() => send("panel")}>Add</Button>
      </RowContainer>
    </PopupContainer>
  );
};

export default Popup;

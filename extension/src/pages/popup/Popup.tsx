import styled from "@emotion/styled";
import { faTableColumns } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";

const PopupContainer = styled.div`
  width: 320px;
  padding: 20px;
  background-color: #f3f3f3;
  font-family: Pretendard, serif;
`;

const RowContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  margin-top: 16px;
`;

const Title = styled.h1`
  font-size: 20px;
  font-weight: bold;
  color: #000000;
  margin: 0;
`;

const TitleContainer = styled(RowContainer)`
  margin-top: 0;
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
  cursor: pointer;
`;

const TextArea = styled.textarea`
  box-sizing: border-box;
  width: 100%;
  height: 140px;
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
  cursor: pointer;
`;

const IconBtn = styled(FontAwesomeIcon)`
  height: 16px;
  color: #242830;
  cursor: pointer;
`;

const Popup = () => {
  const [name, setName] = useState("");
  const [tagName, setTagName] = useState("");
  const groups = [{ id: 1, name: "그룹1" }]; // TODO: state화 하고 API 연결
  const [tags, setTags] = useState([]);

  const send = (type: "add" | "panel") => {
    chrome.runtime.sendMessage({ type });
  };

  const onTagInputEnter = (e) => {
    if (e.key === "Enter") {
      if (!tags.includes(tagName)) {
        setTags([...tags, tagName]);
      }
      setTagName("");
    }
  };

  const onTagClick = (e) => {
    setTags(tags.filter((value) => value != e.target.textContent));
  };

  return (
    <PopupContainer>
      <TitleContainer>
        <Title>Add Bookmark</Title>
        <IconBtn icon={faTableColumns} onClick={() => send("panel")} />
      </TitleContainer>

      <RowContainer>
        <Label>Name</Label>
        <Input onChange={(e) => setName(e.target.value)} value={name} />
      </RowContainer>

      <RowContainer>
        <Label>Group</Label>
        <Select>
          <option value={0} key={0} disabled selected>
            Select
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
        <Input
          onKeyDown={onTagInputEnter}
          onChange={(e) => setTagName(e.target.value)}
          value={tagName}
        />
      </RowContainer>
      <TagContainer>
        {tags.map((value, index) => (
          <Tag key={index} onClick={onTagClick}>
            {value}
          </Tag>
        ))}
      </TagContainer>

      <RowContainer>
        <Label>Summary</Label>
      </RowContainer>
      <RowContainer>
        <TextArea></TextArea>
      </RowContainer>

      <RowContainer>
        <Button onClick={() => send("add")}>Add</Button>
      </RowContainer>
    </PopupContainer>
  );
};

export default Popup;

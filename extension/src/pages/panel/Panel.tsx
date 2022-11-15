import styled from "@emotion/styled";
import { faSearch, faGear } from "@fortawesome/free-solid-svg-icons";
import { faFolder } from "@fortawesome/free-regular-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import logo from "../../assets/img/logo.png";

const PanelContainer = styled.div`
  display: grid;
  grid-template-columns: 320px auto;
  width: 100%;
  min-height: 100vh;
  background-color: #f3f3f3;
  font-family: Pretendard, serif;
`;

const LeftContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: #242830;
`;

const RightContainer = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
`;

const Title = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  height: 60px;
  background-color: #1f2227;
  cursor: default;
  font-size: 20px;
  font-weight: bold;
  color: #ffffff;

  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
`;

const Logo = styled.img`
  height: 24px;
`;

const SectionContainer = styled.div`
  box-sizing: border-box;
  display: flex;
  gap: 12px;
  flex-direction: column;
  width: 100%;
  padding: 20px;

  & > * {
    font-size: 16px;
    color: #d4d4d4;
  }
`;

const SectionTitle = styled.div`
  font-size: 16px;
  font-weight: bold;
`;

const TagContainer = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: center;
  justify-content: flex-start;
  gap: 4px;
`;

const Tag = styled.div`
  box-sizing: border-box;
  width: fit-content;
  border-radius: 8px;
  font-size: 12px;
  padding: 8px;
  background-color: #d4d4d4;
  color: #000000;
  cursor: pointer;
`;

const SectionIconBtn = styled.button`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 8px;
  padding: 4px 0;
  background-color: transparent;
  border: 0;
  outline: 0;
  cursor: pointer;
`;

const SectionIcon = styled(FontAwesomeIcon)`
  height: 14px;
`;

const Panel = () => {
  const [tags, setTags] = useState(["tag1"]);

  return (
    <PanelContainer>
      <LeftContainer>
        <Title>
          <Logo src={logo} alt="logo" />
          Bookmarker
        </Title>
        <SectionContainer>
          <SectionIconBtn>
            <SectionIcon icon={faSearch} />
            Search
          </SectionIconBtn>
          <SectionIconBtn>
            <SectionIcon icon={faGear} />
            Settings
          </SectionIconBtn>
        </SectionContainer>
        <SectionContainer>
          <SectionTitle>Group</SectionTitle>
          <SectionIconBtn>
            <SectionIcon icon={faFolder} />
            group1
          </SectionIconBtn>
        </SectionContainer>
        <SectionContainer>
          <SectionTitle>Tag</SectionTitle>
          <TagContainer>
            {tags.map((value, index) => (
              <Tag key={index}>{value}</Tag>
            ))}
          </TagContainer>
        </SectionContainer>
      </LeftContainer>
      <RightContainer>right</RightContainer>
    </PanelContainer>
  );
};

export default Panel;

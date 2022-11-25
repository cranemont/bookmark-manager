import {
  Box,
  Button,
  ChakraProvider,
  Flex,
  Heading,
  IconButton,
  Input,
  Select,
  Tag,
  TagCloseButton,
  TagLabel,
  Textarea,
} from "@chakra-ui/react";
import { faTableColumns } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import axios from "axios";
import { useEffect, useState } from "react";

// const PopupContainer = styled.div`
//   width: 320px;
//   padding: 20px;
//   background-color: #f3f3f3;
// `;

// const RowContainer = styled.div`
//   display: flex;
//   flex-direction: row;
//   align-items: center;
//   justify-content: space-between;
//   margin-top: 16px;
// `;

// const Title = styled.h1`
//   font-size: 20px;
//   font-weight: bold;
//   color: #000000;
//   margin: 0;
// `;

// const TitleContainer = styled(RowContainer)`
//   margin-top: 0;
// `;

// const TagContainer = styled(RowContainer)`
//   flex-wrap: wrap;
//   gap: 4px;
//   justify-content: flex-start;
// `;

// const Label = styled.h2`
//   font-size: 16px;
//   font-weight: bold;
//   color: #3c3c3c;
//   margin: 0;
// `;

// const Input = styled.input`
//   box-sizing: border-box;
//   width: 250px;
//   border-radius: 8px;
//   border: thin solid #eeeeee;
//   font-size: 16px;
//   padding: 8px;
//   background-color: #ffffff;
// `;

// const Select = styled.select`
//   box-sizing: border-box;
//   width: 250px;
//   border-radius: 8px;
//   border: thin solid #eeeeee;
//   font-size: 16px;
//   padding: 8px;
//   background-color: #ffffff;
// `;

// const Tag = styled.div`
//   box-sizing: border-box;
//   width: fit-content;
//   border-radius: 8px;
//   font-size: 12px;
//   padding: 8px;
//   background-color: #d4d4d4;
//   cursor: pointer;
// `;

// const TextArea = styled.textarea`
//   box-sizing: border-box;
//   width: 100%;
//   height: 140px;
//   resize: none;
//   border-radius: 8px;
//   font-size: 16px;
//   padding: 8px;
//   border: thin solid #eeeeee;
// `;

// const Button = styled.button`
//   width: 100%;
//   border-radius: 8px;
//   border: thin solid #eeeeee;
//   font-size: 16px;
//   padding: 12px;
//   background-color: #242830;
//   color: #ffffff;
//   font-weight: bold;
//   cursor: pointer;
// `;

// const IconBtn = styled(FontAwesomeIcon)`
//   height: 16px;
//   color: #242830;
//   cursor: pointer;
// `;

const Popup = () => {
  const [url, setUrl] = useState("");
  const [name, setName] = useState("");
  const [summary, setSummary] = useState("");
  const [tagName, setTagName] = useState("");
  const groups = [{ id: 1, name: "그룹1" }]; // TODO: state화 하고 API 연결
  const [tags, setTags] = useState([]);

  const getTestData = async () => {
    await ((timeToDelay) =>
      new Promise((resolve) => setTimeout(resolve, timeToDelay)))(1000);
    return {
      topics: ["one", "two", "three"],
      summary: "summary",
    };
  };

  const init = async () => {
    // get current page info and update state
    const [tab] = await chrome.tabs.query({
      active: true,
      lastFocusedWindow: true,
    });
    setUrl(tab.url);
    setName(tab.title);

    // request nlp api and update state
    const { data } = await axios.post(
      "http://43.201.119.242/nlp/summarize",
      {
        url: tab.url,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    console.log(data);
    data.tags && setTags(data.tags);
    data.summary && setSummary(data.summary);
  };

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

  const removeTag = (tagValue: string) => {
    setTags(tags.filter((value) => value != tagValue));
  };

  useEffect(() => {
    init();
  }, []);

  return (
    <ChakraProvider>
      <Box width={96} padding={5}>
        <Flex alignItems="center" justifyContent="space-between">
          <Heading as="h1" size="md">
            Add Bookmark
          </Heading>
          <IconButton
            aria-label="Panel Link"
            icon={<FontAwesomeIcon icon={faTableColumns} />}
            variant="ghost"
            onClick={() => send("panel")}
          />
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Url
          </Heading>
          <Input
            maxWidth={64}
            placeholder="Url"
            onChange={(e) => setUrl(e.target.value)}
            value={url}
          />
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Name
          </Heading>
          <Input
            maxWidth={64}
            placeholder="Name"
            onChange={(e) => setName(e.target.value)}
            value={name}
          />
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Group
          </Heading>
          <Select maxWidth={64} placeholder="Select">
            {groups.map(({ id, name }) => (
              <option value={id} key={id}>
                {name}
              </option>
            ))}
          </Select>
        </Flex>

        <Flex marginY={4} alignItems="center" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Tags
          </Heading>
          <Input
            maxWidth={64}
            placeholder="Tags"
            onKeyDown={onTagInputEnter}
            onChange={(e) => setTagName(e.target.value)}
            value={tagName}
          />
        </Flex>
        <Flex marginY={4} alignItems="center" flexWrap="wrap" gap={1}>
          {tags.map((value, index) => (
            <Tag key={index}>
              <TagLabel>{value}</TagLabel>
              <TagCloseButton onClick={() => removeTag(value)} />
            </Tag>
          ))}
        </Flex>

        <Flex marginY={4} flexDirection="column" justifyContent="space-between">
          <Heading as="h2" size="sm">
            Summary
          </Heading>
          <Textarea
            marginTop={4}
            height="140px"
            value={summary}
            resize="none"
          ></Textarea>
        </Flex>

        <Flex marginTop={4} alignItems="center" justifyContent="space-between">
          <Button width="full" onClick={() => send("add")}>
            Add
          </Button>
        </Flex>
      </Box>
    </ChakraProvider>
  );
};

export default Popup;

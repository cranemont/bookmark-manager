import {
  Button,
  Flex,
  Heading,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  ModalProps,
  Select,
  Tag,
  TagCloseButton,
  TagLabel,
  Textarea,
} from "@chakra-ui/react";
import { useEffect, useState } from "react";

type Props = Omit<ModalProps, "children">;

export const PanelBookmarkEditModal = ({
  isOpen,
  onClose,
  bookmark,
  update,
  groups,
  ...props
}: Props & { bookmark: any; update: any; groups: any }) => {
  const [tags, setTags] = useState(bookmark?.tags);
  const [inputValue, setInputValue] = useState({
    url: "",
    title: "",
    summary: "",
    group: "",
    tag: "",
  });

  const onTagInputEnter = (e) => {
    if (e.key === "Enter") {
      if (!tags.includes(inputValue.tag)) {
        setTags([...tags, inputValue.tag]);
      }
      setInputValue((prev) => ({ ...prev, tag: "" }));
    }
  };

  const removeTag = (tagValue: string) => {
    setTags(tags.filter((value) => value != tagValue));
  };

  useEffect(() => {
    setInputValue({
      url: bookmark?.url,
      title: bookmark?.title,
      summary: bookmark?.summary,
      group: bookmark?.group,
      tag: "",
    });
    setTags(bookmark?.tags);
  }, [bookmark]);

  return (
    <Modal isOpen={isOpen} onClose={onClose} {...props}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Update bookmark</ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          <Flex marginY={4} alignItems="center" justifyContent="space-between">
            <Heading as="h2" size="sm">
              Url
            </Heading>
            <Input
              maxWidth={64}
              placeholder="Url"
              onChange={(e) =>
                setInputValue((prev) => ({ ...prev, url: e.target.value }))
              }
              value={inputValue.url}
            />
          </Flex>

          <Flex marginY={4} alignItems="center" justifyContent="space-between">
            <Heading as="h2" size="sm">
              Title
            </Heading>
            <Input
              maxWidth={64}
              placeholder="title"
              onChange={(e) =>
                setInputValue((prev) => ({ ...prev, title: e.target.value }))
              }
              value={inputValue.title}
            />
          </Flex>

          <Flex marginY={4} alignItems="center" justifyContent="space-between">
            <Heading as="h2" size="sm">
              Group
            </Heading>
            <Select
              maxWidth={64}
              placeholder="Select"
              value={inputValue.group}
              onChange={(e) =>
                setInputValue((prev) => ({ ...prev, group: e.target.value }))
              }
            >
              {groups?.map((value, index) => (
                <option value={value} key={index}>
                  {value}
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
              onChange={(e) =>
                setInputValue((prev) => ({ ...prev, tag: e.target.value }))
              }
              value={inputValue.tag}
            />
          </Flex>
          <Flex marginY={4} alignItems="center" flexWrap="wrap" gap={1}>
            {tags?.map((value, index) => (
              <Tag key={index}>
                <TagLabel>{value}</TagLabel>
                <TagCloseButton onClick={() => removeTag(value)} />
              </Tag>
            ))}
          </Flex>

          <Flex
            marginY={4}
            flexDirection="column"
            justifyContent="space-between"
          >
            <Heading as="h2" size="sm">
              Summary
            </Heading>
            <Textarea
              marginTop={4}
              height={28}
              resize="none"
              onChange={(e) =>
                setInputValue((prev) => ({ ...prev, summary: e.target.value }))
              }
              value={inputValue.summary}
            />
          </Flex>
        </ModalBody>
        <ModalFooter>
          <Button
            colorScheme="blue"
            mr={3}
            onClick={async () => {
              await update(bookmark.id, {
                url: inputValue.url,
                title: inputValue.title,
                summary: inputValue.summary,
                tags: tags,
                group: inputValue.group,
              });
              onClose();
            }}
          >
            Edit
          </Button>
          <Button onClick={onClose}>Cancel</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};
